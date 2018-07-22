<div>
    <style type="text/css">
        .s1 {
            font-weight: bold;
            color: black;
        }

        .s2 {
            color: black;
            text-decoration: underline;
        }

        .s3 {
            color: black;
        }

        .p1 {
            text-align: center;
            hyphenate: auto;
            font-family: Times New Roman;
            font-size: 26pt;
        }

        .p2 {
            text-align: center;
            hyphenate: auto;
            font-family: Times New Roman;
            font-size: 16pt;
        }

        .p3 {
            text-align: start;
            hyphenate: auto;
            font-family: Times New Roman;
            font-size: 16pt;
        }

        .p4 {
            text-indent: 0.44444445in;
            text-align: justify;
            hyphenate: auto;
            font-family: Times New Roman;
            font-size: 16pt;
        }

        .p5 {
            text-indent: 0.44444445in;
            text-align: justify;
            hyphenate: auto;
            font-family: 宋体;
            font-size: 16pt;
        }
    </style>
    <p class="p1">
        <span class="s1">网上预审材料清单</span>
    </p>
    <p class="p2"></p>
    <p class="p3">
        <span class="s2">            </span>
    </p>
    <p class="p4">
        <span class="s3">您在网上预审环节中上传了如下材料：</span>
    </p>
    <#list uploadMaterialList as item>
        <p class="p4">
            <span class="s3"> ${item_index+1}、${item.name}</span>
        </p>
    </#list>
    <p class="p5">
        <span class="s3">预审步骤中未上传的材料项，视为不提交此项材料，受理期内不再接受材料补充。预审意见做出以后，受理期内无法对个人信息及上传预审材料进行修改。</span>
    </p>
</div>
