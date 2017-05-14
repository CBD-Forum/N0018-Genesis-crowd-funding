<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%
String path = request.getContextPath();
%>
<div class="jm_banner">
   <div class="btn"></div>
</div>

<div class="box" style="background:#fff;">
    
    <div class="jmhz_art clearfix">
        <h2>爱筹网分公司加盟详情</h2>
        <p class="width100">加盟电话: 0351-5280800 &nbsp;&nbsp;&nbsp;&nbsp;0351-5280811</p>
        <hr class="width100" />
        <div style="width:715px;color:#666;font-size:14px;line-height:24px;margin-top:67px;">
            <p>爱筹网众筹平台正式成立于2015年4月，是一个集筹好业、筹好货、筹爱心、筹好房于一体的综合众筹平台，主要致力于为中小型企业、实体店铺、优秀项目融资、孵化、运营等一站式服务，为投资人对接优质项目。</p><br />
            <p>爱筹网众筹平台采用线下-线上的O2O模式，即线下发展众筹基地/俱乐部，配合线上拓展并服务会员，线上投资项目。众筹基地/俱乐部负责人即分公司负责人，由其组建分公司运营团队。全国线下基地/俱乐部分省级、地市县两级，采用加盟模式。省级众筹基地/俱乐部由总部控股，并参与管理，地市县众筹基地/俱乐部由省级众筹基地/俱乐部负责筹备运营管理。前期只招募省级众筹基地/俱乐部加盟方。</p><br />
        </div>
    </div>
    <div class="jmhz_tu">
        <img src="<%=path%>/images/mapG.png" />
        <div class="jmhz_bq">
            <p class="jmhz_red">* 红色：已开通分站</p>
            <p class="jmhz_gray">* 白色：未签约分站</p>
        </div>
        <!-- <div class="jmhz_sous">搜索城市<input type="text" /><span class="jmhz_fdj"><img src="<%=path%>/images/fdj.png" /></span>
        <br /><span> <i>太原、大同、运城、晋中、阳泉</i></span></div> -->
    </div>
    <div class="jmhz_city">
        <div class="jmhz_hd jmhz_citydiv on">
            华东
            <span name="btn"><i>-</i>收缩</span>
        </div>
        <div class="jmhz_citydetial">
            <div class="jmhz_sd">
                <span>山东</span>
                <ul>
                    <li><a href="javascript:void(0);">青岛</a></li>
                    <li><a href="javascript:void(0);">济南</a></li>
                    <li><a href="javascript:void(0);">烟台</a></li>
                    <li><a href="javascript:void(0);">潍坊</a></li>
                    <li><a href="javascript:void(0);">临沂</a></li>
                    <li><a href="javascript:void(0);">淄博</a></li>
                    <li><a href="javascript:void(0);">济宁</a></li>
                    <li><a href="javascript:void(0);">泰安</a></li>
                    <li><a href="javascript:void(0);">聊城</a></li>
                    <li><a href="javascript:void(0);">威海</a></li>
                    <li><a href="javascript:void(0);">枣庄</a></li>
                    <li><a href="javascript:void(0);">德州</a></li>
                    <li><a href="javascript:void(0);">日照</a></li>
                    <li><a href="javascript:void(0);">东营</a></li>
                    <li><a href="javascript:void(0);">菏泽</a></li>
                    <li><a href="javascript:void(0);">滨州</a></li>
                    <li><a href="javascript:void(0);">莱芜</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>上海</span>
                <ul>
                    <li><a href="javascript:void(0);">上海</li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>江苏</span>
                <ul>
                    <li><a href="javascript:void(0);">苏州</a></li>
                    <li><a href="javascript:void(0);">南京</a></li>
                    <li><a href="javascript:void(0);">无锡</a></li>
                    <li><a href="javascript:void(0);">常州</a></li>
                    <li><a href="javascript:void(0);">徐州</a></li>
                    <li><a href="javascript:void(0);">南通</a></li>
                    <li><a href="javascript:void(0);">扬州</a></li>
                    <li><a href="javascript:void(0);">盐城</a></li>
                    <li><a href="javascript:void(0);">淮安</a></li>
                    <li><a href="javascript:void(0);">连云港</a></li>
                    <li><a href="javascript:void(0);">泰州</a></li>
                    <li><a href="javascript:void(0);">宿迁</a></li>
                    <li><a href="javascript:void(0);">镇江</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>浙江</span>
                <ul>
                    <li><a href="javascript:void(0);">杭州</a></li>
                    <li><a href="javascript:void(0);">宁波</a></li>
                    <li><a href="javascript:void(0);">温州</a></li>
                    <li><a href="javascript:void(0);">金华</a></li>
                    <li><a href="javascript:void(0);">嘉兴</a></li>
                    <li><a href="javascript:void(0);">台州</a></li>
                    <li><a href="javascript:void(0);">绍兴</a></li>
                    <li><a href="javascript:void(0);">湖州</a></li>
                    <li><a href="javascript:void(0);">丽水</a></li>
                    <li><a href="javascript:void(0);">衢州</a></li>
                    <li><a href="javascript:void(0);">舟山</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>安徽</span>
                <ul>
                    <li><a href="javascript:void(0);">合肥</a></li>
                    <li><a href="javascript:void(0);">芜湖</a></li>
                    <li><a href="javascript:void(0);">蚌埠</a></li>
                    <li><a href="javascript:void(0);">阜阳</a></li>
                    <li><a href="javascript:void(0);">淮南</a></li>
                    <li><a href="javascript:void(0);">安庆</a></li>
                    <li><a href="javascript:void(0);">宿州</a></li>
                    <li><a href="javascript:void(0);">六安</a></li>
                    <li><a href="javascript:void(0);">淮北</a></li>
                    <li><a href="javascript:void(0);">滁州</a></li>
                    <li><a href="javascript:void(0);">马鞍山</a></li>
                    <li><a href="javascript:void(0);">铜陵</a></li>
                    <li><a href="javascript:void(0);">宣城</a></li>
                    <li><a href="javascript:void(0);">亳州</a></li>
                    <li><a href="javascript:void(0);">黄山</a></li>
                    <li><a href="javascript:void(0);">池州</a></li>
                    <li><a href="javascript:void(0);">巢湖</a></li>
                </ul>
            </div>
        </div>
        
        <div class="jmhz_hd jmhz_citydiv on">
            华南
            <span name="btn"><i>+</i>展开</span>
        </div>
        <div class="jmhz_citydetial" style="display:none;">
            <div class="jmhz_sd">
                <span>福建</span>
                <ul>
                    <li><a href="javascript:void(0);">福州</a></li>
                    <li><a href="javascript:void(0);">厦门</a></li>
                    <li><a href="javascript:void(0);">莆田</a></li>
                    <li><a href="javascript:void(0);">三明</a></li>
                    <li><a href="javascript:void(0);">泉州</a></li>
                    <li><a href="javascript:void(0);">漳州</a></li>
                    <li><a href="javascript:void(0);">南平</a></li>
                    <li><a href="javascript:void(0);">龙岩</a></li>
                    <li><a href="javascript:void(0);">宁德</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>广东</span>
                <ul>
                    <li><a href="javascript:void(0);">广州</a></li>
                    <li><a href="javascript:void(0);">韶关</a></li>
                    <li><a href="javascript:void(0);">深圳</a></li>
                    <li><a href="javascript:void(0);">珠海</a></li>
                    <li><a href="javascript:void(0);">汕头</a></li>
                    <li><a href="javascript:void(0);">佛山</a></li>
                    <li><a href="javascript:void(0);">江门</a></li>
                    <li><a href="javascript:void(0);">湛江</a></li>
                    <li><a href="javascript:void(0);">茂名</a></li>
                    <li><a href="javascript:void(0);">肇庆</a></li>
                    <li><a href="javascript:void(0);">惠州</a></li>
                    <li><a href="javascript:void(0);">梅州</a></li>
                    <li><a href="javascript:void(0);">汕尾</a></li>
                    <li><a href="javascript:void(0);">河源</a></li>
                    <li><a href="javascript:void(0);">阳江</a></li>
                    <li><a href="javascript:void(0);">清远</a></li>
                    <li><a href="javascript:void(0);">东莞</a></li>
                    <li><a href="javascript:void(0);">中山</a></li>
                    <li><a href="javascript:void(0);">潮州</a></li>
                    <li><a href="javascript:void(0);">揭阳</a></li>
                    <li><a href="javascript:void(0);">云浮</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>广西</span>
                <ul>
                    <li><a href="javascript:void(0);">南宁</a></li>
                    <li><a href="javascript:void(0);">柳州</a></li>
                    <li><a href="javascript:void(0);">桂林</a></li>
                    <li><a href="javascript:void(0);">梧州</a></li>
                    <li><a href="javascript:void(0);">北海</a></li>
                    <li><a href="javascript:void(0);">防城港</a></li>
                    <li><a href="javascript:void(0);">钦州</a></li>
                    <li><a href="javascript:void(0);">贵港</a></li>
                    <li><a href="javascript:void(0);">玉林</a></li>
                    <li><a href="javascript:void(0);">百色</a></li>
                    <li><a href="javascript:void(0);">贺州</a></li>
                    <li><a href="javascript:void(0);">河池</a></li>
                    <li><a href="javascript:void(0);">来宾</a></li>
                    <li><a href="javascript:void(0);">崇左</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>海南 </span>
                <ul>
                    <li><a href="javascript:void(0);">海口</a></li>
                    <li><a href="javascript:void(0);">三亚</a></li>
                    <li><a href="javascript:void(0);">直辖县</a></li>
                </ul>
            </div>
        </div>
        
        <div class="jmhz_hd jmhz_citydiv on">
            中南
            <span name="btn"><i>+</i>展开</span>
        </div>
        <div class="jmhz_citydetial" style="display:none;">
            <div class="jmhz_sd">
                <span>江西</span>
                <ul>
                    <li><a href="javascript:void(0);">南昌</a></li>
                    <li><a href="javascript:void(0);">景德镇</a></li>
                    <li><a href="javascript:void(0);">萍乡</a></li>
                    <li><a href="javascript:void(0);">九江</a></li>
                    <li><a href="javascript:void(0);">新余</a></li>
                    <li><a href="javascript:void(0);">鹰潭</a></li>
                    <li><a href="javascript:void(0);">赣州</a></li>
                    <li><a href="javascript:void(0);">吉安</a></li>
                    <li><a href="javascript:void(0);">宜春</a></li>
                    <li><a href="javascript:void(0);">抚州</a></li>
                    <li><a href="javascript:void(0);">上饶</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>河南</span>
                <ul>
                    <li><a href="javascript:void(0);">郑州</a></li>
                    <li><a href="javascript:void(0);">开封</a></li>
                    <li><a href="javascript:void(0);">洛阳</a></li>
                    <li><a href="javascript:void(0);">平顶山</a></li>
                    <li><a href="javascript:void(0);">安阳</a></li>
                    <li><a href="javascript:void(0);">鹤壁</a></li>
                    <li><a href="javascript:void(0);">新乡</a></li>
                    <li><a href="javascript:void(0);">焦作</a></li>
                    <li><a href="javascript:void(0);">济源</a></li>
                    <li><a href="javascript:void(0);">濮阳</a></li>
                    <li><a href="javascript:void(0);">许昌</a></li>
                    <li><a href="javascript:void(0);">漯河</a></li>
                    <li><a href="javascript:void(0);">三门峡</a></li>
                    <li><a href="javascript:void(0);">南阳</a></li>
                    <li><a href="javascript:void(0);">商丘</a></li>
                    <li><a href="javascript:void(0);">信阳</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>湖北</span>
                <ul>
                    <li><a href="javascript:void(0);">武汉</a></li>
                    <li><a href="javascript:void(0);">黄石</a></li>
                    <li><a href="javascript:void(0);">十堰</a></li>
                    <li><a href="javascript:void(0);">宜昌</a></li>
                    <li><a href="javascript:void(0);">襄阳</a></li>
                    <li><a href="javascript:void(0);">鄂州</a></li>
                    <li><a href="javascript:void(0);">荆门</a></li>
                    <li><a href="javascript:void(0);">孝感</a></li>
                    <li><a href="javascript:void(0);">荆州</a></li>
                    <li><a href="javascript:void(0);">黄冈</a></li>
                    <li><a href="javascript:void(0);">咸宁</a></li>
                    <li><a href="javascript:void(0);">随州</a></li>
                    <li><a href="javascript:void(0);">恩施</a></li>
                    <li><a href="javascript:void(0);">仙桃市</a></li>
                    <li><a href="javascript:void(0);">潜江市</a></li>
                    <li><a href="javascript:void(0);">天门市</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>湖南 </span>
                <ul>
                    <li><a href="javascript:void(0);">长沙</a></li>
                    <li><a href="javascript:void(0);">株洲</a></li>
                    <li><a href="javascript:void(0);">湘潭</a></li>
                    <li><a href="javascript:void(0);">衡阳</a></li>
                    <li><a href="javascript:void(0);">邵阳</a></li>
                    <li><a href="javascript:void(0);">岳阳</a></li>
                    <li><a href="javascript:void(0);">常德</a></li>
                    <li><a href="javascript:void(0);">张家界</a></li>
                    <li><a href="javascript:void(0);">益阳</a></li>
                    <li><a href="javascript:void(0);">郴州</a></li>
                    <li><a href="javascript:void(0);">永州</a></li>
                    <li><a href="javascript:void(0);">怀化</a></li>
                    <li><a href="javascript:void(0);">娄底</a></li>
                    <li><a href="javascript:void(0);">湘西</a></li>
                </ul>
            </div>
        </div>
        
        <div class="jmhz_hd jmhz_citydiv on">
            东北
            <span name="btn"><i>+</i>展开</span>
        </div>
        <div class="jmhz_citydetial" style="display:none;">
            <div class="jmhz_sd">
                <span>辽宁</span>
                <ul>
                    <li><a href="javascript:void(0);">沈阳</a></li>
                    <li><a href="javascript:void(0);">大连</a></li>
                    <li><a href="javascript:void(0);">鞍山</a></li>
                    <li><a href="javascript:void(0);">抚顺</a></li>
                    <li><a href="javascript:void(0);">本溪</a></li>
                    <li><a href="javascript:void(0);">丹东</a></li>
                    <li><a href="javascript:void(0);">锦州</a></li>
                    <li><a href="javascript:void(0);">营口</a></li>
                    <li><a href="javascript:void(0);">阜新</a></li>
                    <li><a href="javascript:void(0);">辽阳</a></li>
                    <li><a href="javascript:void(0);">盘锦</a></li>
                    <li><a href="javascript:void(0);">铁岭</a></li>
                    <li><a href="javascript:void(0);">朝阳</a></li>
                    <li><a href="javascript:void(0);">葫芦岛</a></li>
                    
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>吉林</span>
                <ul>
                    <li><a href="javascript:void(0);">长春</a></li>
                    <li><a href="javascript:void(0);">吉林</a></li>
                    <li><a href="javascript:void(0);">四平</a></li>
                    <li><a href="javascript:void(0);">辽源</a></li>
                    <li><a href="javascript:void(0);">通化</a></li>
                    <li><a href="javascript:void(0);">白山</a></li>
                    <li><a href="javascript:void(0);">松原</a></li>
                    <li><a href="javascript:void(0);">白城</a></li>
                    <li><a href="javascript:void(0);">延边</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>黑龙江</span>
                <ul>
                    <li><a href="javascript:void(0);">哈尔滨</a></li>
                    <li><a href="javascript:void(0);">齐齐哈尔</a></li>
                    <li><a href="javascript:void(0);">鸡西</a></li>
                    <li><a href="javascript:void(0);">鹤岗</a></li>
                    <li><a href="javascript:void(0);">双鸭山</a></li>
                    <li><a href="javascript:void(0);">大庆</a></li>
                    <li><a href="javascript:void(0);">伊春</a></li>
                    <li><a href="javascript:void(0);">佳木斯</a></li>
                    <li><a href="javascript:void(0);">七台河</a></li>
                    <li><a href="javascript:void(0);">牡丹江</a></li>
                    <li><a href="javascript:void(0);">黑河</a></li>
                    <li><a href="javascript:void(0);">绥化</a></li>
                    <li><a href="javascript:void(0);">大兴安岭</a></li>
                </ul>
            </div>
        </div>
        
        <div class="jmhz_hd jmhz_citydiv on">
            西南
            <span name="btn"><i>+</i>展开</span>
        </div>
        <div class="jmhz_citydetial" style="display:none;">
            <div class="jmhz_sd">
                <span>重庆</span>
                <ul>
                    <li><a href="javascript:void(0);">万州区</a></li>
                    <li><a href="javascript:void(0);">涪陵区</a></li>
                    <li><a href="javascript:void(0);">渝中区</a></li>
                    <li><a href="javascript:void(0);">大渡口</a></li>
                    <li><a href="javascript:void(0);">沙坪坝</a></li>
                    <li><a href="javascript:void(0);">九龙坡</a></li>
                    <li><a href="javascript:void(0);">南岸区</a></li>
                    <li><a href="javascript:void(0);">北碚区</a></li>
                    <li><a href="javascript:void(0);">万盛区</a></li>
                    <li><a href="javascript:void(0);">双桥区</a></li>
                    <li><a href="javascript:void(0);">渝北区</a></li>
                    <li><a href="javascript:void(0);">巴南区</a></li>
                    <li><a href="javascript:void(0);">黔江区</a></li>
                    <li><a href="javascript:void(0);">长寿区</a></li>
                    <li><a href="javascript:void(0);">潼南县</a></li>
                    <li><a href="javascript:void(0);">铜梁县</a></li>
                    <li><a href="javascript:void(0);">大足县</a></li>
                    <li><a href="javascript:void(0);">荣昌县</a></li>
                    <li><a href="javascript:void(0);">璧山县</a></li>
                    <li><a href="javascript:void(0);">梁平县</a></li>
                    <li><a href="javascript:void(0);">城口县</a></li>
                    <li><a href="javascript:void(0);">丰都县</a></li>
                    <li><a href="javascript:void(0);">垫江县</a></li>
                    <li><a href="javascript:void(0);">武隆县</a></li>
                    <li><a href="javascript:void(0);">忠县</a></li>
                    <li><a href="javascript:void(0);">开县</a></li>
                    <li><a href="javascript:void(0);">云阳县</a></li>
                    <li><a href="javascript:void(0);">奉节县</a></li>
                    <li><a href="javascript:void(0);">巫山县</a></li>
                    <li><a href="javascript:void(0);">巫溪县</a></li>
                    <li><a href="javascript:void(0);">石柱县</a></li>
                    <li><a href="javascript:void(0);">秀山县</a></li>
                    <li><a href="javascript:void(0);">酉阳县</a></li>
                    <li><a href="javascript:void(0);">彭水县</a></li>
                    <li><a href="javascript:void(0);">江津市</a></li>
                    <li><a href="javascript:void(0);">合川市</a></li>
                    <li><a href="javascript:void(0);">永川市</a></li>
                    <li><a href="javascript:void(0);">南川市</a></li>
                    
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>四川</span>
                <ul>
                    <li><a href="javascript:void(0);">成都</a></li>
                    <li><a href="javascript:void(0);">自贡</a></li>
                    <li><a href="javascript:void(0);">攀枝花</a></li>
                    <li><a href="javascript:void(0);">泸州</a></li>
                    <li><a href="javascript:void(0);">德阳</a></li>
                    <li><a href="javascript:void(0);">绵阳</a></li>
                    <li><a href="javascript:void(0);">广元</a></li>
                    <li><a href="javascript:void(0);">遂宁</a></li>
                    <li><a href="javascript:void(0);">内江</a></li>
                    <li><a href="javascript:void(0);">乐山</a></li>
                    <li><a href="javascript:void(0);">南充</a></li>
                    <li><a href="javascript:void(0);">眉山</a></li>
                    <li><a href="javascript:void(0);">宜宾</a></li>
                    <li><a href="javascript:void(0);">广安</a></li>
                    <li><a href="javascript:void(0);">达州</a></li>
                    <li><a href="javascript:void(0);">雅安</a></li>
                    <li><a href="javascript:void(0);">巴中</a></li>
                    <li><a href="javascript:void(0);">资阳</a></li>
                    <li><a href="javascript:void(0);">阿坝</a></li>
                    <li><a href="javascript:void(0);">甘孜</a></li>
                    <li><a href="javascript:void(0);">凉山</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>贵州</span>
                <ul>
                    <li><a href="javascript:void(0);">贵阳</a></li>
                    <li><a href="javascript:void(0);">盘水</a></li>
                    <li><a href="javascript:void(0);">遵义</a></li>
                    <li><a href="javascript:void(0);">安顺</a></li>
                    <li><a href="javascript:void(0);">铜仁</a></li>
                    <li><a href="javascript:void(0);">黔西南</a></li>
                    <li><a href="javascript:void(0);">毕节地</a></li>
                    <li><a href="javascript:void(0);">黔东南</a></li>
                    <li><a href="javascript:void(0);">黔南</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>云南</span>
                <ul>
                    <li><a href="javascript:void(0);">昆明</a></li>
                    <li><a href="javascript:void(0);">曲靖</a></li>
                    <li><a href="javascript:void(0);">玉溪</a></li>
                    <li><a href="javascript:void(0);">保山</a></li>
                    <li><a href="javascript:void(0);">昭通</a></li>
                    <li><a href="javascript:void(0);">丽江</a></li>
                    <li><a href="javascript:void(0);">思茅</a></li>
                    <li><a href="javascript:void(0);">临沧</a></li>
                    <li><a href="javascript:void(0);">楚雄</a></li>
                    <li><a href="javascript:void(0);">红河</a></li>
                    <li><a href="javascript:void(0);">文山</a></li>
                    <li><a href="javascript:void(0);">西双版纳</a></li>
                    <li><a href="javascript:void(0);">大理</a></li>
                    <li><a href="javascript:void(0);">德宏</a></li>
                    <li><a href="javascript:void(0);">怒江傈</a></li>
                    <li><a href="javascript:void(0);">迪庆</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>西藏</span>
                <ul>
                    <li><a href="javascript:void(0);">拉萨</a></li>
                    <li><a href="javascript:void(0);">昌都</a></li>
                    <li><a href="javascript:void(0);">山南</a></li>
                    <li><a href="javascript:void(0);">日喀则</a></li>
                    <li><a href="javascript:void(0);">那曲</a></li>
                    <li><a href="javascript:void(0);">阿里</a></li>
                    <li><a href="javascript:void(0);">林芝</a></li>
                </ul>
            </div>
        </div>
        
        <div class="jmhz_hd jmhz_citydiv on">
            华北
            <span name="btn"><i>+</i>展开</span>
        </div>
        <div class="jmhz_citydetial" style="display:none;">
            <div class="jmhz_sd">
                <span>北京</span>
                <ul>
                    <li><a href="javascript:void(0);">东城区</a></li>
                    <li><a href="javascript:void(0);">西城区</a></li>
                    <li><a href="javascript:void(0);">崇文区</a></li>
                    <li><a href="javascript:void(0);">宣武区</a></li>
                    <li><a href="javascript:void(0);">朝阳区</a></li>
                    <li><a href="javascript:void(0);">丰台区</a></li>
                    <li><a href="javascript:void(0);">石景山</a></li>
                    <li><a href="javascript:void(0);">海淀区</a></li>
                    <li><a href="javascript:void(0);">门头沟</a></li>
                    <li><a href="javascript:void(0);">房山区</a></li>
                    <li><a href="javascript:void(0);">通州区</a></li>
                    <li><a href="javascript:void(0);">顺义区</a></li>
                    <li><a href="javascript:void(0);">昌平区</a></li>
                    <li><a href="javascript:void(0);">大兴区</a></li>
                    <li><a href="javascript:void(0);">怀柔区</a></li>
                    <li><a href="javascript:void(0);">平谷区</a></li>
                    <li><a href="javascript:void(0);">密云县</a></li>
                    <li><a href="javascript:void(0);">延庆县</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>天津</span>
                <ul>
                    <li><a href="javascript:void(0);">和平区</a></li>
                    <li><a href="javascript:void(0);">河东区</a></li>
                    <li><a href="javascript:void(0);">河西区</a></li>
                    <li><a href="javascript:void(0);">南开区</a></li>
                    <li><a href="javascript:void(0);">河北区</a></li>
                    <li><a href="javascript:void(0);">红桥区</a></li>
                    <li><a href="javascript:void(0);">塘沽区</a></li>
                    <li><a href="javascript:void(0);">汉沽区</a></li>
                    <li><a href="javascript:void(0);">大港区</a></li>
                    <li><a href="javascript:void(0);">东丽区</a></li>
                    <li><a href="javascript:void(0);">西青区</a></li>
                    <li><a href="javascript:void(0);">津南区</a></li>
                    <li><a href="javascript:void(0);">北辰区</a></li>
                    <li><a href="javascript:void(0);">武清区</a></li>
                    <li><a href="javascript:void(0);">宝坻区</a></li>
                    <li><a href="javascript:void(0);">静海县</a></li>
                    <li><a href="javascript:void(0);">蓟县</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>河北</span>
                <ul>
                    <li><a href="javascript:void(0);">石家庄</a></li>
                    <li><a href="javascript:void(0);">唐山</a></li>
                    <li><a href="javascript:void(0);">秦皇岛</a></li>
                    <li><a href="javascript:void(0);">邯郸</a></li>
                    <li><a href="javascript:void(0);">邢台</a></li>
                    <li><a href="javascript:void(0);">保定</a></li>
                    <li><a href="javascript:void(0);">张家口</a></li>
                    <li><a href="javascript:void(0);">承德</a></li>
                    <li><a href="javascript:void(0);">沧州</a></li>
                    <li><a href="javascript:void(0);">廊坊</a></li>
                    <li><a href="javascript:void(0);">衡水</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span class="orgJ">山西</span>
                <ul>
                    <li><a href="javascript:void(0);" class="orgJ">太原</a></li>
                    <li><a href="javascript:void(0);">大同</a></li>
                    <li><a href="javascript:void(0);">阳泉</a></li>
                    <li><a href="javascript:void(0);">长治</a></li>
                    <li><a href="javascript:void(0);">晋城</a></li>
                    <li><a href="javascript:void(0);">朔州</a></li>
                    <li><a href="javascript:void(0);">晋中</a></li>
                    <li><a href="javascript:void(0);">运城</a></li>
                    <li><a href="javascript:void(0);">忻州</a></li>
                    <li><a href="javascript:void(0);">临汾</a></li>
                    <li><a href="javascript:void(0);">吕梁</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>内蒙古</span>
                <ul>
                    <li><a href="javascript:void(0);">呼和浩特</a></li>
                    <li><a href="javascript:void(0);">包头</a></li>
                    <li><a href="javascript:void(0);">乌海</a></li>
                    <li><a href="javascript:void(0);">赤峰</a></li>
                    <li><a href="javascript:void(0);">通辽</a></li>
                    <li><a href="javascript:void(0);">鄂尔多斯</a></li>
                    <li><a href="javascript:void(0);">呼伦贝尔</a></li>
                    <li><a href="javascript:void(0);">巴彦淖尔</a></li>
                    <li><a href="javascript:void(0);">乌兰察布</a></li>
                    <li><a href="javascript:void(0);">兴安盟</a></li>
                    <li><a href="javascript:void(0);">锡林郭勒</a></li>
                    <li><a href="javascript:void(0);">阿拉善盟</a></li>
                </ul>
            </div>
        </div>
        
        <div class="jmhz_hd jmhz_citydiv on">
            西北
            <span name="btn"><i>+</i>展开</span>
        </div>
        <div class="jmhz_citydetial" style="display:none;">
            <div class="jmhz_sd">
                <span>陕西</span>
                <ul>
                    <li><a href="javascript:void(0);">西安</a></li>
                    <li><a href="javascript:void(0);">铜川</a></li>
                    <li><a href="javascript:void(0);">宝鸡</a></li>
                    <li><a href="javascript:void(0);">咸阳</a></li>
                    <li><a href="javascript:void(0);">渭南</a></li>
                    <li><a href="javascript:void(0);">延安</a></li>
                    <li><a href="javascript:void(0);">汉中</a></li>
                    <li><a href="javascript:void(0);">榆林</a></li>
                    <li><a href="javascript:void(0);">安康</a></li>
                    <li><a href="javascript:void(0);">商洛</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>甘肃</span>
                <ul>
                    <li><a href="javascript:void(0);">兰州</a></li>
                    <li><a href="javascript:void(0);">嘉峪关</a></li>
                    <li><a href="javascript:void(0);">金昌</a></li>
                    <li><a href="javascript:void(0);">白银</a></li>
                    <li><a href="javascript:void(0);">天水</a></li>
                    <li><a href="javascript:void(0);">武威</a></li>
                    <li><a href="javascript:void(0);">张掖</a></li>
                    <li><a href="javascript:void(0);">平凉</a></li>
                    <li><a href="javascript:void(0);">酒泉</a></li>
                    <li><a href="javascript:void(0);">庆阳</a></li>
                    <li><a href="javascript:void(0);">定西</a></li>
                    <li><a href="javascript:void(0);">陇南</a></li>
                    <li><a href="javascript:void(0);">临夏</a></li>
                    <li><a href="javascript:void(0);">甘南</a></li>
                    
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>青海</span>
                <ul>
                    <li><a href="javascript:void(0);">西宁</a></li>
                    <li><a href="javascript:void(0);">海东</a></li>
                    <li><a href="javascript:void(0);">海北</a></li>
                    <li><a href="javascript:void(0);">黄南</a></li>
                    <li><a href="javascript:void(0);">海南</a></li>
                    <li><a href="javascript:void(0);">果洛</a></li>
                    <li><a href="javascript:void(0);">玉树</a></li>
                    <li><a href="javascript:void(0);">海西</a></li>
                    
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>宁夏</span>
                <ul>
                    <li><a href="javascript:void(0);">银川</a></li>
                    <li><a href="javascript:void(0);">石嘴山</a></li>
                    <li><a href="javascript:void(0);">吴忠</a></li>
                    <li><a href="javascript:void(0);">固原</a></li>
                    <li><a href="javascript:void(0);">中卫</a></li>
                </ul>
            </div>
            <div class="jmhz_sd">
                <span>新疆</span>
                <ul>
                    <li><a href="javascript:void(0);">乌鲁木齐</a></li>
                    <li><a href="javascript:void(0);">克拉玛依</a></li>
                    <li><a href="javascript:void(0);">吐鲁番</a></li>
                    <li><a href="javascript:void(0);">哈密</a></li>
                    <li><a href="javascript:void(0);">昌吉</a></li>
                    <li><a href="javascript:void(0);">博尔塔拉</a></li>
                    <li><a href="javascript:void(0);">巴音郭楞</a></li>
                    <li><a href="javascript:void(0);">阿克苏</a></li>
                    <li><a href="javascript:void(0);">克孜勒苏</a></li>
                    <li><a href="javascript:void(0);">喀什</a></li>
                    <li><a href="javascript:void(0);">和田</a></li>
                    <li><a href="javascript:void(0);">伊犁</a></li>
                    <li><a href="javascript:void(0);">塔城</a></li>
                    <li><a href="javascript:void(0);">阿勒泰</a></li>
                    <li><a href="javascript:void(0);">石河子</a></li>
                    <li><a href="javascript:void(0);">阿拉尔</a></li>
                    <li><a href="javascript:void(0);">图木舒克</a></li>
                    <li><a href="javascript:void(0);">五家渠</a></li>
                </ul>
            </div>
        </div>
        
        <div class="jmhz_hd jmhz_citydiv on">
            其他
            <span name="btn"><i>+</i>展开</span>
        </div>
        <div class="jmhz_citydetial" style="display:none;">
            <div class="jmhz_sd">
                <span>其它</span>
                <ul>
                    <li><a href="javascript:void(0);">台湾</a></li>
                    <li><a href="javascript:void(0);">香港</a></li>
                    <li><a href="javascript:void(0);">澳门</a></li>
                </ul>
            </div>
        </div>
    </div>
    
    <div class="jmhz_art">
        <h2 class="jmhz_hzms">文件下载</h2>
        <div class="jmhz_download clearfix">
        <div style="width:910px;">
            <!-- <a href="javascript:void(0);" class="clearfix abox">
            	<img src="<%=path%>/images/download.png" class="fl"/>
                <p class="fl">爱筹网分站<br />加盟协议</p>
            </a> -->
            <a href="<%=path %>/htmlforpdf/getContractView.html?nodeType=join_agreement" class="clearfix abox" style="margin-left:300px;">
            	<img src="<%=path%>/images/download.png" class="fl"/>
                <p class="fl">爱筹网分公司<br />加盟协议书</p>
            </a>
            <!-- <a href="javascript:void(0);" class="clearfix abox">
            	<img src="<%=path%>/images/download.png" class="fl"/>
                <p class="fl">爱筹网分站<br />加盟协议</p>
            </a> -->
        </div>
        </div>
    </div>
</div>
<input id="indexFor" type="hidden" namefor="jmhz"/>
<script type="text/javascript">
	$(function(){
		$("span[name='btn']").click(function(){
			if($(this).parent().next().css("display") == "none"){ //所属模块是关闭状态,就展开
				$(this).parent().next().fadeIn();
				$(this).html('<i>-</i>收缩');
			}else{
				$(this).parent().next().fadeOut();
				$(this).html('<i>+</i>展开');
			}
		});
	});
</script>