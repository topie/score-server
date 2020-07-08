<style type="text/css">
    td {
        text-align: center;
        vertical-align: 2 middle;
        border: .5pt solid #000000;
        font-style: normal;
        font-weight: 400;
        font-size: 11.0pt;
    }
</style>
<div>
    <table width="2470" style="border-collapse:collapse;width:1852.53pt;border:2pt solid #000000;">
        <tbody>
        <tr>
            <td colspan="18" height="46" width="2407" style="font-size:18.0pt;font-weight:700;">
                天津市居住证积分审核表(市卫健委)
            </td>
        </tr>
        <tr>
            <td height="20" width="93">
            </td>
            <td height="20" width="62">
                姓名
            </td>
            <td height="20" width="72">
                曾用名
            </td>
            <td height="20" width="72">
                性别
            </td>
            <td height="20" width="187">
                身份证号码
            </td>
            <td height="20" width="125">
                婚姻状况
            </td>
            <td height="20" width="72">
                佐证材料
            </td>
            <td colspan="6" height="20" width="601">
                户籍地地址
            </td>
            <td colspan="5" height="20" width="1123">
                现居住地址
            </td>
        </tr>
        <tr>
            <td height="20" width="93">
                申请人情况
            </td>
            <td height="20" width="62">
                ${person.name}
            </td>
            <td height="20" width="72">
                ${person.formerName}
            </td>
            <td height="20" width="72">
                ${person.getStringSex()}
            </td>
            <td height="20" width="187">
                ${person.idNumber}
            </td>
            <td height="20" width="125">
                ${houseMove.getMarriageStatusStr()}
            </td>
            <td height="20" width="72">
                -
            </td>
            <td colspan="6" height="20" width="601">
                ${houseMove.moveAddress}
            </td>
            <td colspan="5" height="20" width="1123">
                ${houseMove.witnessAddress}
            </td>
        </tr>
        <tr>
            <td height="20" width="93">
                配偶情况
            </td>
            <td height="20" width="62">
                <#if Spouse>${Spouse.name}</#if>
            </td>
            <td height="20" width="72">
                <#if Spouse>${Spouse.formerName}</#if>
            </td>
            <td height="20">
                <#if Spouse>${Spouse.getStringSex()}</#if>
            </td>
            <td height="20" width="187">
                <#if Spouse>${Spouse.idNumber}</#if>
            </td>
            <td height="20">
                <#if Spouse>${Spouse.getStringMarriageStatus()}</#if>
            </td>
            <td height="20" width="72">
                -
            </td>
            <td colspan="6" height="20" width="601">
                <#if Spouse>${Spouse.spouse_HJAddress}</#if>
            </td>
            <td colspan="5" height="20" width="1123">
                <#if Spouse>${Spouse.spouse_LivingAddress}</#if>
            </td>
        </tr>
        <tr>
            <td colspan="2" height="20" width="186">
                申请人联系电话
            </td>
            <td colspan="3" height="20" width="216">
                ${other.selfPhone}
            </td>
            <td colspan="2" height="20" width="250">
                申请人单位电话
            </td>
            <td colspan="2" height="20" width="124">
                ${other.companyPhone}
            </td>
            <td colspan="2" height="20" width="124">
                申请人单位名称
            </td>
            <td colspan="3" height="20" width="311">
                ${other.companyName}
            </td>
            <td height="20" width="187">
                申请人单位地址
            </td>
            <td colspan="3" height="20" width="1061">
                ${other.companyAddress}
            </td>
        </tr>
        <tr>
            <td colspan="18" height="43" width="2407">
                以下填写申请人及配偶的子女情况，包括亲生子女（具体指婚生子女、非婚生子女、离异时抚养权判予对方的子女）和收养子女。
            </td>
        </tr>
        <tr>
            <td rowspan="2" height="40" width="93">
                序号
            </td>
            <td rowspan="2" height="40" width="62">
                姓名
            </td>
            <td rowspan="2" height="40" width="72">
                曾用名
            </td>
            <td rowspan="2" height="40" width="72">
                性别
            </td>
            <td rowspan="2" height="40" width="187">
                身份证号码
            </td>
            <td colspan="3" height="20" width="322">
                出生医学证明
            </td>
            <td rowspan="2" height="40" width="250">
                出生地
            </td>
            <td colspan="2" height="20" width="124">
                收养子女
            </td>
            <td rowspan="2" height="40" width="93">
                政策属性
            </td>
            <td colspan="4" height="20" width="593">
                再生育审批情况
            </td>
            <#--<td rowspan="2" height="40" width="187">-->
                <#--与第几任妻子/丈夫所生-->
            <#--</td>-->
            <td rowspan="2" colspan="2" height="40" width="156">
                申请人本人亲生子女
            </td>
        </tr>
        <tr>
            <td height="20" width="125">
                编号
            </td>
            <td height="20" width="72">
                签证机构
            </td>
            <td height="20" width="62">
                佐证材料
            </td>
            <td height="20" width="62">
                是/否
            </td>
            <td height="20" width="72">
                佐证材料
            </td>
            <td height="20" width="125">
                审批时间
            </td>
            <td height="20" width="156">
                审批证明编号
            </td>
            <td height="20" width="187">
                审批单位名称
            </td>
            <td height="20" width="437">
                审批条例适用
            </td>
        </tr>
        <#list relationshipList as ritem>
            <tr>
                <td height="40" width="93">
                    ${ritem_index+1}
                </td>
                <td height="40" width="62">
                    ${ritem.name}
                </td>
                <td height="40" width="72">
                    ${ritem.formerName}
                </td>
                <td height="40">
                    ${ritem.getStringSex()}
                </td>
                <td height="40" width="187">
                    ${ritem.idNumber}
                </td>
                <td height="40" width="125">
                    ${ritem.medical_number}
                </td>
                <td height="40" width="72">
                    ${ritem.medical_authority}
                </td>
                <td height="40" width="62">
                    -
                </td>
                <td height="40" width="250">
                    ${ritem.birthplace}
                </td>
                <td height="40">
                    ${ritem.getStringIsAdopt()}
                </td>
                <td height="40" width="72">
                    -
                </td>
                <td height="40" width="93">
                    ${ritem.policyAttribute}
                </td>
                <td height="40">
                    ${ritem.approval_time}
                </td>
                <td height="40" width="156">
                    ${ritem.approval_number}
                </td>
                <td height="40" width="187">
                    ${ritem.approval_companyName}
                </td>
                <td height="40" width="437">
                    ${ritem.approval_rules}
                </td>
                <#--<td height="40" width="187">-->
                    <#--&lt;#&ndash;${ritem.approval_which}&ndash;&gt;-->
                    <#--与第${ritem.approval_index}任${ritem.approval_spouse} ${ritem.approval_which}所生-->
                <#--</td>-->
                <td height="40" width="156">
                    <#if ritem.isNaturalChild == 1>是</#if>
                    <#if ritem.isNaturalChild == 2>否</#if>
                </td>
            </tr>
        </#list>
        <tr>
            <td colspan="18" height="40" width="2407">
            </td>
        </tr>
        <tr>
            <td colspan="2" height="20" width="186">
            </td>
            <td colspan="5" height="20" width="528">
                本人及配偶承诺目前${person.getStringPregnantPromise()} ${person.pregnantWeek}周
            </td>
            <td colspan="5" height="20" width="508">
                本人及配偶承诺目前 ${person.getStringThirdPregnantPromise()} 政策外生育（或收养）第三个及以上子女，${person.getStringContractOrCertificate()} 政策外怀孕第三个及以上子女
            </td>
            <td colspan="6" height="20" width="1217">
            </td>
        </tr>
        </tbody>
    </table>
    <br/>
</div>

