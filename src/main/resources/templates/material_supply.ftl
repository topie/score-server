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
            材料补正
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
                            <table style="font-size: 14px;" class="table table-hover table-bordered table-condensed">
                                <tr class="info">
                                    <th>选择</th>
                                    <th>预览(点击放大)</th>
                                    <th class="text-info">材料名称</th>
                                </tr>
                                <#list materialInfos as item>
                                    <#if item.onlinePersonMaterial??>
                                        <tr>
                                            <td class="text-center">
                                                <input name="supplyMaterial" value="${item.id?c}" type="checkbox">
                                            </td>
                                            <td class="text-center">
                                                <img class="p-img-supply"
                                                     style="cursor: pointer;border: 1px solid gray;"
                                                     width="100" height="100"
                                                     src="${item.onlinePersonMaterial.materialUri}">
                                            </td>
                                            <td>${item.onlinePersonMaterial.materialInfoName}
                                                <br>
                                                <button class="download-supply btn btn-mini btn-info" type="button"
                                                        data-uri="${item.onlinePersonMaterial.materialUri}"
                                                        data-name="${item.onlinePersonMaterial.materialInfoName}_${item.onlinePersonMaterial.personId?c}">
                                                    下载
                                                </button>
                                            </td>
                                        </tr>
                                    <#else>
                                        <tr>
                                            <td class="text-center"><input name="supplyMaterial" value="${item.id?c}"
                                                                           type="checkbox"></td>
                                            <td class="text-center">
                                                未上传
                                            </td>
                                            <td>${item.name}</td>
                                        </tr>
                                    </#if>
                                    <tr style="display: none;">
                                        <td>补件理由：</td>
                                        <td colspan="2"><input name="supplyReason" class="form-control"></td>
                                    </tr>
                                </#list>
                            </table>
                            <script type="text/javascript">
                                var hostName = window.location.host;
                                if (hostName == "172.16.200.68") {
                                    $(".p-img-supply").each(function () {
                                        var src = $(this).attr("src");
                                        var newSrc = src.replace("218.67.246.52:80", "172.16.200.68:8092");
                                        $(this).attr("src",newSrc);
                                    });
                                    $("a.download-supply").each(function () {
                                        var href = $(this).attr("href");
                                        var newHref = href.replace("218.67.246.52:80", "172.16.200.68:8092");
                                        $(this).attr("href", newHref);
                                    });
                                }
                                $(".p-img-supply").off("click");
                                $(".p-img-supply").on("click", function () {
                                    var img = $('<img src="' + $(this).attr("src") + '">');
                                    $.orangeModal({
                                        title: "图片预览",
                                        destroy: true
                                    }).show().$body.html(img);
                                });
                                $(".download-supply").off("click");
                                $(".download-supply").on("click", function () {
                                    var uri = $(this).attr("data-uri");
                                    var name = $(this).attr("data-name");
                                    var type = uri.substring(uri.lastIndexOf("."));
                                    var img = $("<a></a>").attr("href", uri).attr("download", name + type);
                                    img[0].click();
                                });
                                $("input[name=supplyMaterial]").on("change", function () {
                                    if ($(this).is(":checked")) {
                                        $(this).parent().parent().next("tr").show();
                                    } else {
                                        $(this).parent().parent().next("tr").hide();
                                    }
                                });
                            </script>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

