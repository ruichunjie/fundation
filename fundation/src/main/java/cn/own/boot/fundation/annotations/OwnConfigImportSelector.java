package cn.own.boot.fundation.annotations;

        import cn.own.boot.fundation.config.ContextConfig;
        import org.springframework.context.annotation.ImportSelector;
        import org.springframework.core.type.AnnotationMetadata;

/**
 * @description: 选择器
 * @author: xinYue
 * @time: 2019/11/19 9:26
 */
public class OwnConfigImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{ContextConfig.class.getName()};
    }
}
