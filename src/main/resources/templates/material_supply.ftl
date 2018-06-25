<style>
    .table-list-item {
        padding: 5px 5px;
    }

    .table-list-item .table {
        margin-bottom: 0;
    }
</style>
<div class="widget-box transparent">
    <div class="widget-header">
        <h4 class="widget-title lighter smaller">
            材料上传
        </h4>


    </div>

    <div class="widget-body">
        <div class="widget-main">
            <div class="tab-content">
                <div id="info-tab" class="row tab-pane active">
                    <div class="panel panel-default">
                        <!-- Default panel contents -->
                        <div class="panel-heading">
                            申请人材料上传情况
                        </div>
                        <!-- Table 多个表格列表组合 -->
                        <div class="table-list-item">
                            <table class="table table-hover table-bordered table-condensed">
                                <tr class="info">
                                    <th>选择</th>
                                    <th>预览(点击放大)</th>
                                    <th class="text-info">材料名称</th>
                                </tr>
                                <#list onlinePersonMaterials as item>
                                    <#if item.materialInfoId gt 0>
                                        <tr>
                                            <input name="mId" value="${item.id}"
                                                   type="checkbox"/>
                                            <td class="text-center">
                                                <img class="p-img" id="img_${item.id}" style="cursor: pointer"
                                                     width="100" height="100" src="${item.materialUri}">
                                            </td>
                                            <td>${item.materialInfoName}
                                                <br>
                                                <button class="download btn btn-mini btn-info" type="button"
                                                        data-uri="${item.materialUri}"
                                                        data-name="${item.materialInfoName}_${item.personId?c}">下载
                                                </button>
                                            </td>
                                        </tr>
                                    </#if>
                                </#list>
                            </table>
                            <script type="text/javascript">
                                $(".p-img").off("click");
                                $(".p-img").on("click", function () {
                                    var img = $('<img src="' + $(this).attr("src") + '">');
                                    $.orangeModal({
                                        title: "图片预览",
                                        destroy: true
                                    }).show().$body.html(img);
                                });
                                $(".download").off("click");
                                $(".download").on("click", function () {
                                    var uri = $(this).attr("data-uri");
                                    var name = $(this).attr("data-name");
                                    var type = uri.substring(uri.lastIndexOf("."));
                                    var img = $("<a></a>").attr("href", uri).attr("download", name + type);
                                    img[0].click();
                                });
                            </script>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

