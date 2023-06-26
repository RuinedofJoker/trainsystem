train-manage是vue2的后台端

train-vue3为vue3开发的前台端（主要的业务端）

trainsystem-backend为spring-boot开发的后端

文档里面有项目的sql结构和测试数据以及开发文档

部署：
先部署springboot的后端:
进入trainsystem-backend里的train-admin模块进入里面的application-druid.yml文件，配置数据库连接信息。
其中sql文件在文档的zybtrainsystem-testdata.sql（zybtrainsystem-structure.sql文件只有数据库结构，不包含测试数据）
然后再application.yml中配置train.profile（图片等静态资源存放位置）和redis的连接信息
然后可以运行train-admin模块下的springboot的main方法

部署vue3的前端前台端：
进入train-vue3，使用npm i 命令下载依赖，然后使用npm run dev命令运行dev环境，npm run build:prod命令打包（推荐node版本16~18）

部署vue2的前端后台端：
进入train-manage，使用npm i 命令下载依赖，然后使用npm run dev命令运行dev环境，npm run build:prod命令打包（推荐node版本12）
