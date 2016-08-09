package me.chunyu.iocprocessor;

import com.sun.tools.javac.code.Attribute;
import com.sun.tools.javac.code.Symbol;
import com.sun.tools.javac.code.Type;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import me.chunyu.ioc.BroadcastRecvr;
import me.chunyu.ioc.IoCProcessor;

/**
 * Created by linyun on 16/5/19.
 */
public class ProcessorImpl extends AbstractProcessor {

    private Map<Symbol.ClassSymbol, List<Element>> mClass2AnnotatedEleMap = new HashMap<>();

    private Filer mFiler;
    private VelocityEngine mEngine;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mEngine = new VelocityEngine();
        mEngine.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, "iocprocessor/src/main/resources");
        mEngine.init();
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new LinkedHashSet<>();
        set.add(BroadcastRecvr.class.getCanonicalName());
        return set;
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        for (TypeElement annotation : set) {
            Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(annotation);
            for (Element annotatedEle : annotatedElements) {
                Symbol.ClassSymbol enclosingClass = (Symbol.ClassSymbol) annotatedEle.getEnclosingElement();
                List<Element> annotatedElementList = mClass2AnnotatedEleMap.get(enclosingClass);
                if (annotatedElementList == null) {
                    annotatedElementList = new ArrayList<>();
                    mClass2AnnotatedEleMap.put(enclosingClass, annotatedElementList);
                }
                annotatedElementList.add(annotatedEle);
            }
        }

        for (Symbol.ClassSymbol classSymbol : mClass2AnnotatedEleMap.keySet()) {
            genProcessorFile(classSymbol);
        }
        return false;
    }

    private void genProcessorFile(Symbol.ClassSymbol classSymbol) {
        // 准备模板的context
        Template template = mEngine.getTemplate("ProcessorTemplate.java");
        VelocityContext vcontext = new VelocityContext();

        String packageName = getPackageName(classSymbol);
        String className = classSymbol.getSimpleName().toString();
        String processorName = className + IoCProcessor.POST_FIX;

        vcontext.put("packageName", packageName);
        vcontext.put("className", className);
        vcontext.put("processorName", processorName);

        List<Object> broadcastInfos = new ArrayList<>();
        vcontext.put("broadcastInfos", broadcastInfos);

        // 对每个annotatedElement的每个annotationMirror进行循环
        List<Element> annotatedElementList = mClass2AnnotatedEleMap.get(classSymbol);
        for (Element annotatedEle : annotatedElementList) {
            List<? extends AnnotationMirror> annotationMirrors = annotatedEle.getAnnotationMirrors();
            for (AnnotationMirror annotation : annotationMirrors) {
                String annotationName = annotation.getAnnotationType().toString();
                if (BroadcastRecvr.class.getName().equals(annotationName)) {
                    broadcastInfos.add(getBroadcastInfo(annotatedEle, annotation));
                }
            }
        }

        Writer templateWriter = new StringWriter();
        template.merge(vcontext, templateWriter);

        try {
            JavaFileObject jfo = mFiler.createSourceFile(processorName);
            Writer fileWriter = jfo.openWriter();
            fileWriter.write(templateWriter.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    private Map<String, Object> getBroadcastInfo(Element element, AnnotationMirror annotation) {
        BroadcastRecvr broadcastRecvr = element.getAnnotation(BroadcastRecvr.class);

        Map<String, Object> broadcastInfo = new HashMap<>();

        List<String> actions = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        Collections.addAll(actions, broadcastRecvr.actions());
        Collections.addAll(categories, broadcastRecvr.categories());

        // get action info
        Map<? extends ExecutableElement, ? extends AnnotationValue> annotationItems = annotation.getElementValues();
        Set<? extends ExecutableElement> keySet = annotationItems.keySet();
        for (ExecutableElement key : keySet) {
            // actionTypes
            if (BroadcastRecvr.ACTION_TYPES.equals(key.getSimpleName().toString())) {
                Attribute.Array actionTypes = (Attribute.Array) annotationItems.get(key);
                for (Attribute attr : actionTypes.values) {
                    actions.add(attr.getValue().toString());
                }
            }
            // categoryTypes
            if (BroadcastRecvr.CATEGORY_TYPES.equals(key.getSimpleName().toString())) {
                Attribute.Array categoryTypes = (Attribute.Array) annotationItems.get(key);
                for (Attribute attr : categoryTypes.values) {
                    categories.add(attr.getValue().toString());
                }
            }
        }
        broadcastInfo.put("actions", actions);
        broadcastInfo.put("categories", categories);

        // get method info
        Symbol.MethodSymbol methodSymbol = (Symbol.MethodSymbol) element;
        Type.MethodType methodType = (Type.MethodType) methodSymbol.asType();
        StringBuilder methodBuilder = new StringBuilder();
        List<Type> parameters = new ArrayList<>(methodType.getParameterTypes());
        if (parameters.size() > 0
                && "android.content.Context".equals(parameters.get(0).asElement().getQualifiedName().toString())) {
            methodBuilder.append(", context");
            parameters.remove(0);
        }
        if (parameters.size() > 0
                && "android.content.Intent".equals(parameters.get(0).asElement().getQualifiedName().toString())) {
            methodBuilder.append(", intent");
            parameters.remove(0);
        }
        for (Type param : parameters) {
            String dataType = param.toString();
            methodBuilder.append(String.format(Locale.getDefault(),
                    ", (%s) intent.getExtras().get(\"%s\")", dataType, dataType));
        }
        methodBuilder.delete(0, 2);
        methodBuilder.insert(0, methodSymbol.getSimpleName() + "(");
        methodBuilder.append(")");

        broadcastInfo.put("method", methodBuilder.toString());
        return broadcastInfo;
    }

    private String getPackageName(Symbol.ClassSymbol classSymbol) {
        Element packageElement = classSymbol;
        do {
            packageElement = packageElement.getEnclosingElement();
        } while (packageElement != null && !(packageElement instanceof PackageElement));
        return packageElement.toString();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
