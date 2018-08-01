# 临床学术追踪系统
# 项目环境

JDK：JDK 1.8
tomcat: tomcat8

## 功能点：


## 问题：


{
     "button":[
     {   
          "type":"view",
          "name":"问卷中心",
          "url":"http://cats.kydev.net/user/survey/surveyNew"
     },{
           "type":"view",
           "name":"操作指南",
           "url":"http://cats.kydev.net/guide/guideList"
     },{   
          "name":"会员中心",
           "sub_button":[
           {   
               "type":"view",
               "name":"个人信息",
               "url":"http://cats.kydev.net/user/userInfo/infoList"
           },
           {
               "type":"view",
               "name":"我的钱包",
               "url":"http://cats.kydev.net/user/wallet/recordList"
           },
           {
               "type":"view",
               "name":"联系我们",
               "url":"http://cats.kydev.net/guide/connect"
           }]
     }]
}

      
{
    "menu":{
        "button":[
            {
                "name":"科普教育",
                "sub_button":[
                    {
                        "type":"view",
                        "name":"疾病知识",
                        "url":"http://www.baidu.com",
                        "sub_button":[

                        ]
                    },
                    {
                        "type":"view",
                        "name":"治疗康复",
                        "url":"http://www.baidu.com",
                        "sub_button":[

                        ]
                    }
                ]
            },
            {
                "name":"服务与支持",
                "sub_button":[
                    {
                        "type":"view",
                        "name":"联系我们",
                        "url":"http://www.baidu.com222",
                        "sub_button":[

                        ]
                    }
                ]
            }
        ]
    }
}