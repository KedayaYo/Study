package com.tth.service.config.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;

import java.util.List;

public class CustomizedSqlInjector extends DefaultSqlInjector {
    /**
     * 如果只需增加方法，保留mybatis plus自带方法，
     * 可以先获取super.getMethodList()，再添加add
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
      List<AbstractMethod> methods = super.getMethodList(mapperClass,tableInfo);
      // 自定义的insert SQL注入器
      methods.add(new InsertBatchColumn());
      // 自定义的update SQL注入器，参数需要与RootMapper的批量update名称一致
      methods.add(new UpdateBatchColumn("updateBatch"));
      return methods;
    }
}
