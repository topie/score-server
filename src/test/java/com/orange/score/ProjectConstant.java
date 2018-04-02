package com.orange.score;

/**
 * 项目常量
 */
public final class ProjectConstant {

    public static final String BASE_PACKAGE = "com.orange.score";//项目基础包名称，根据自己公司的项目修改

    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".database.*.model";//Model所在包

    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".database.*.dao";//Mapper所在包

    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".module.*.service";//Service所在包

    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";//ServiceImpl所在包

    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".module.*.controller";//Controller所在包

    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".common.core.Mapper";//Mapper插件基础接口的完全限定名
}
