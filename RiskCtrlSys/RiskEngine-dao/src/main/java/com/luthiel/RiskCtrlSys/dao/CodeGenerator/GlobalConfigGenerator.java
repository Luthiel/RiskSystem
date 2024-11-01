package com.luthiel.RiskCtrlSys.dao.CodeGenerator;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.luthiel.RiskCtrlSys.commons.constants.ConstantsUtil;

import java.util.function.Consumer;

/**
 * author: Luthiel
 * description: MybatisPlus 代码自动生成器 全局配置
 * date: 2023
 */

public class GlobalConfigGenerator implements Consumer<GlobalConfig.Builder> {
    @Override
    public void accept(GlobalConfig.Builder builder) {
        builder.author(ConstantsUtil.AUTHOR)
                .commentDate(ConstantsUtil.DATE)
                // 输出目录 (src/main/java的目录绝对路径), user.dir -> 用户当前工作目录
                .outputDir(System.getProperty("user.dir"))
                // 开启 swagger 模式
                .enableSwagger()
                //禁止打开输出目录
                .disableOpenDir();
        }

}
