<div style="width:595.3pt;margin-bottom:72.0pt;margin-top:72.0pt;margin-left:90.0pt;margin-right:90.0pt;"><p><span
        style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">申报编号：</span><span
        style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">${person.acceptNumber}</span></p>
    <p style="text-align:center;white-space:pre-wrap;"><span
            style="font-size:22.0pt;font-weight:bold;color:#000000;white-space:pre-wrap;">天津市居住证积分材料接收凭证</span></p>
    <p style="text-align:left;white-space:pre-wrap;"><br/></p>
    <p style="text-align:left;white-space:pre-wrap;"><span style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">申请人姓名：${person.name}</span>
    </p>
    <p style="text-indent:32.0pt;text-align:left;white-space:pre-wrap;"><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">您于</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${year}</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">年</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${month}</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">月</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${day}</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">日，向本机关提出的</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">居住证积分入户</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">申请所提交的下列申请材料，本机关已收到：</span></p>
    <#list mList as item>
        <p style="margin-left:32.0pt;text-align:left;white-space:pre-wrap;"><span
                style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">${item.materialName}</span></p>
    </#list>


    <p style="text-indent:32.0pt;text-align:left;white-space:pre-wrap;"><br/></p>
    <p style="text-align:left;white-space:pre-wrap;"><span style="color:#000000;white-space:pre-wrap;">市人力社保局窗口审核留存的原件，申请人（或者经办人）请于本期入户名单公示后</span><span
            style="color:#000000;white-space:pre-wrap;">6</span><span style="color:#000000;white-space:pre-wrap;">个月内持申请人身份证和本凭证取回，逾期不再承担保管职责。（受理部门是市人力社保局时显示）</span>
    </p>
    <p style="text-align:right;white-space:pre-wrap;"><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${nowYear}</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">年</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${nowMonth}</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">月</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${nowDay}</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">日</span></p>
    <p style="text-align:left;white-space:pre-wrap;"><span style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">受理部门：</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">  </span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${department}</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">   </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">        </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">受理时间：</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;"> </span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${now}</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">   </span></p>
    <p style="text-align:left;white-space:pre-wrap;"><span style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">受</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;"> </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">理</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;"> </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">人：</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">  </span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${user.displayName}</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">  </span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">   </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">        </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">送达时间：</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;"> </span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${now}</span></p>
    <p style="text-align:left;white-space:pre-wrap;"><span style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">经</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;"> </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">办</span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;"> </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">人：</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">  </span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${user.displayName}</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">     </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">        </span><span
            style="font-size:16.0pt;color:#000000;white-space:pre-wrap;">联系电话：</span><span
            style="font-size:16.0pt;color:#000000;text-decoration:underline;white-space:pre-wrap;">${user.contactPhone}</span>
    </p></div>
