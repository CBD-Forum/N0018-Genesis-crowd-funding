function pyRegisterCvt() {
    var q = window,
    n = document,
    m = encodeURIComponent;
    var p = location.href,
    o = n.referrer,
    l, k = n.cookie,
    j = k.match(/(^|;)\s*ipycookie=([^;]*)/),
    a = k.match(/(^|;)\s*ipys ession=([^;]*)/);
    if (q.parent != q) {
        l = p;
        p = o;
        o = l
    }
    u = "//stats.ipinyou.com/cvt?a=" + m("p.bZs.YhTk42_jF2JviS3y0bnbTX") + "&c =" + m(j ? j[2] : "") + "&s=" + m(a ? a[2].match(/jump\%3D(\d+)/)[1] : "") + "&u=" + m(p) + "&r=" + m(o) + "&rd=" + (new Date()).getTime() + "&e="; (new Image()).src = u
}
var ZC = ZC || {};
ZC.pc = {};
ZC.pc.Version = "2.0.1";
ZC.pc.FileName = "pc.js";
ZC.pc.namespace = function(e) {
    var d = ZC,
    c = "ZC",
    g = e.split("."),
    a = g.length;
    if (c === g[0]) {
        g.shift();
        a--
    }
    for (var b = 0,
    f = ""; b < a; b++) {
        f = g[b];
        if (typeof d[f] === "undefined") {
            d[f] = {}
        }
        d = d[f]
    }
    return d
};
ZC.pc.namespace("ZC.pc.config");
ZC.pc.config = {
    targetList: {
        zhichiBtnBox: "#zhichiBtnBox",
        zhichiBtn: "#zhichiBtn",
        zhichiBtnText: "#zhichiBtnText",
        zhichiCount: "#zhichiCount",
        zhichiIframeBox: "#zhichiIframeBox",
        zhichiIframe: "#zhichiIframe"
    },
    paramNameList: ["sysNum", "source", "tel", "uname", "partnerId", "email", "iframe", "evn"],
    scriptParamList: {},
    urlList: {
        robotLoadAction: "/chat/user/load.action",
        robotVisitAction: "/chat/user/visit.action",
        robotQueryVisitAction: "/chat/user/queryVisit.action"
    },
    pageNow: 1,
    pageSize: 15
};
ZC.pc.namespace("ZC.pc.cacheFlag");
ZC.pc.cacheFlag = {
    isShowedIframe: false,
    isCreatedIframe: false,
    isReinit: false,
    isEnableVisit: false
};
ZC.pc.namespace("ZC.pc.cacheInfo");
ZC.pc.cacheInfo = {
    targetObjList: {},
    targetStrList: {},
    customConfig: {},
    host: {
        name: "",
        urlList: {}
    },
    periodTask: {
        queryVist: null
    },
    aid: "",
    visitId: ""
};
ZC.pc.init = function() {
    var a = ZC.pc;
    a.cacheInfo.urlParamList = a.common.getUrlParam();
    a.cacheInfo.scriptParamList = a.common.getScriptParam();
    a.cacheInfo.host.name = a.common.host.getHost();
    a.cacheInfo.host.urlList = a.common.host.getUrlList();
    a.loadInit()
};
ZC.pc.loadInit = function() {
    var c = ZC.pc,
    d = c.cacheInfo.jsDom,
    a = "",
    b = function(f) {
        if (!c.cacheFlag.isCreatedIframe) {
            c.iframe.createIframeBox();
            c.cacheFlag.isCreatedIframe = true;
            c.updateTargetObjList()
        } else {}
        if (c.cacheFlag.isReinit) {
            window.frames[c.cacheInfo.targetStrList.zhichiIframe].postMessage("zhichiReinit", "*")
        }
        c.pin.effectManip(false, 0);
        c.iframe.effectManip(true)
    };
    c.common.ajax.postAction({
        isDealUrl: true,
        url: "robotLoadAction",
        id: "zhichiJsonp",
        paramList: {
            sysNum: c.cacheInfo.urlParamList.sysNum,
            source: c.cacheInfo.urlParamList.source || 0,
            partnerId: c.cacheInfo.urlParamList.partnerId
        }
    },
    function(f) {
        c.common.extend(c.cacheInfo.customConfig, f);
        if (d && d.className) {
            a = c.common.dom.find("." + d.className);
            for (var e in a) {
                var g = a[e];
                if (g.tagName !== "SCRIPT") {
                    c.common.event.bindTargetEvent(g, "click",
                    function(h) {
                        if (c.cacheFlag.isShowedIframe) {
                            c.iframe.effectManip(false)
                        } else {
                            b(h)
                        }
                        c.common.setCookie("s", 1)
                    })
                }
            }
        } else {
            c.pin.createPin();
            c.common.event.bindTargetEvent(c.cacheInfo.targetObjList.zhichiBtn, "click",
            function(h) {
                b(h);
                c.common.setCookie("s", 1);
                c.cacheInfo.targetObjList.zhichiCount.style.display = "none";
                if (location.href.indexOf("sobot.com") >= 0) {}
            })
        }
        if (c.common.getCookie("s") == "1") {
            b();
            return
        }
        if (!c.cacheFlag.isCreatedIframe && c.cacheInfo.customConfig.isInvite === 0) {
            setTimeout(function() {
                if (c.cacheFlag.isCreatedIframe) {
                    return
                }
                c.common.ajax.postAction({
                    isDealUrl: true,
                    url: "robotVisitAction",
                    id: "zhichiVisit",
                    paramList: {
                        sysNum: c.cacheInfo.urlParamList.sysNum,
                        source: c.cacheInfo.urlParamList.source || 0,
                        title: document.title,
                        uid: f.uid
                    }
                },
                function(j) {
                    if (j && j.visitId) {
                        var h = j.visitId,
                        k = 10000,
                        i = 5 * 60 * 1000;
                        timeCount = 1,
                        timeFunc = function() {
                            c.cacheInfo.periodTask.queryVist = setInterval(function() {
                                if (timeCount * k > i) {
                                    clearInterval(c.cacheInfo.periodTask.queryVist);
                                    return
                                }
                                if (c.cacheFlag.isCreatedIframe) {
                                    return
                                }
                                timeCount++;
                                c.common.ajax.postAction({
                                    isDealUrl: true,
                                    url: "robotQueryVisitAction",
                                    id: "zhichiQueryVisit",
                                    paramList: {
                                        visitId: h
                                    }
                                },
                                function(l) {
                                    if (l && l.type && l.aid && l.type === 207) {
                                        c.cacheFlag.isEnableVisit = true;
                                        c.cacheInfo.aid = l.aid;
                                        c.cacheInfo.visitId = h;
                                        b();
                                        c.common.setCookie("s", 1);
                                        clearInterval(c.cacheInfo.periodTask.queryVist)
                                    }
                                })
                            },
                            k)
                        };
                        timeFunc()
                    }
                })
            },
            1000)
        }
    })
};
ZC.pc.updateTargetStrList = function() {
    var b = ZC.pc,
    c = {};
    for (var a in b.config.targetList) {
        c[a] = b.config.targetList[a].split("#")[1]
    }
    return b.common.extend(b.cacheInfo.targetStrList, c, true)
};
ZC.pc.updateTargetObjList = function() {
    var a = ZC.pc;
    a.common.extend(a.cacheInfo.targetObjList, a.common.dom.getTargetObjList(a.config.targetList), true)
};
ZC.pc.cssEffectManip = function(d, f, b, e) {
    var c = ZC.pc,
    d = typeof d === "object" ? d: null,
    f = typeof f === "object" ? f: null,
    a = "",
    e = e || 500;
    if (d && f && f.effectIn && f.effectOut) {
        a = b ? f.effectIn: f.effectOut;
        setTimeout(function() {
            for (var g in a) {
                d.style[g] = a[g]
            }
        },
        e)
    }
};
ZC.pc.namespace("ZC.pc.common");
ZC.pc.common.formatLan = function(b, a) {
    var d = ZC.pc,
    c = /\{(\d+)\}/g;
    if (c.test(b) && a && d.common.isArray(a)) {
        return b.replace(c,
        function(e, f) {
            return a[f]
        })
    }
    return b
};
ZC.pc.namespace("ZC.pc.common.host");
ZC.pc.common.host.getHost = function() {
    var a = ZC.pc,
    b = window.location.protocol;
    return b + (a.cacheInfo.urlParamList.evn ? "//test.sobot.com": "//www.sobot.com")
};
ZC.pc.common.host.getUrlList = function() {
    var d = ZC.pc,
    h = d.cacheInfo.host.name,
    b = d.cacheInfo.urlParamList,
    f = [],
    c = "",
    g = [],
    e = "";
    if (typeof b === "object") {
        for (var a in b) {
            if (b[a] != null) {
                if (a !== "from") {
                    f.push(a + "=" + b[a])
                }
                g.push(a + "=" + b[a])
            }
        }
    }
    if (f && f.length) {
        c = f.join("&")
    }
    if (c.length) {
        c = "?" + c
    }
    g.push("from=iframe");
    if (g && g.length) {
        e = g.join("&")
    }
    if (e.length) {
        e = "?" + e
    }
    return {
        pc: h + "/chat/pc/index.html" + c + "&r=" + Math.random(),
        pcIframe: h + "/chat/pc/index.html" + e + "&r=" + Math.random()
    }
};
ZC.pc.namespace("ZC.pc.common.ajax");
ZC.pc.namespace("ZC.pc.common.ajax.urlList");
ZC.pc.common.ajax.urlList = ZC.pc.config.urlList;
ZC.pc.common.ajax.dealUrl = function(h) {
    var e = ZC.pc,
    a = e.common.ajax.urlList || e.config.urlList,
    g = {
        host: h.host || e.cacheInfo.host.name,
        actionName: a[h.actionName] || "",
        paramList: h.paramList || null
    },
    f = [],
    d = "",
    c = "";
    if (typeof h.paramList === "object") {
        for (var b in h.paramList) {
            f.push(b + "=" + h.paramList[b])
        }
    }
    if (f && f.length) {
        d = f.join("&")
    }
    c = g.host + g.actionName + "?" + d;
    return c
};
ZC.pc.common.ajax.postAction = function(c, f) {
    var b = ZC.pc,
    e = document.createElement("script"),
    d = c.isDealUrl ? b.common.ajax.dealUrl({
        actionName: c.url,
        paramList: c.paramList
    }) : (c.url ? c.url: ""),
    a = "getback" + (new Date()).getTime();
    d = d + "&callback=" + a;
    e.id = c.id;
    e.setAttribute("src", d);
    document.body.appendChild(e);
    window[a] = function(g) {
        document.body.removeChild(e);
        f && f(g)
    }
};
ZC.pc.common.getUrlParam = function(g, f, b) {
    var d = ZC.pc,
    g = typeof g === "string" ? g: window.location.search,
    e = [],
    b = b === undefined ? 0 : b,
    a = g.substr(1),
    c = function(p, n) {
        var q = "",
        j = "",
        o = "",
        k = n === 0 ? "": {},
        l = "";
        for (var m = 0,
        h = p.length; m < h; m++) {
            q = p[m];
            j = new RegExp("(^|&)" + q + "=([^&]*)(&|$)");
            o = a.match(j);
            l = o != null ? decodeURI(decodeURI(o[2])) : null;
            if (n === 0) {
                k = l
            } else {
                k[q] = l
            }
        }
        return k
    };
    if (f && !(d.common.isArray(f))) {
        e = [f];
        b = 0
    } else {
        if (f && (d.common.isArray(f))) {
            e = f;
            b = 1
        } else {
            e = d.config.paramNameList;
            b = 1
        }
    }
    return c(e, b)
};
ZC.pc.common.isArray = function(a) {
    return Object.prototype.toString.call(a) === "[object Array]"
};
ZC.pc.common.getScriptParam = function() {
    var m = ZC.pc,
    b = m.common.dom.find("#zhichiload"),
    h = 0,
    k = null,
    l,
    g,
    c = [],
    f = 0,
    e = {};
    b = b ? [b] : document.scripts;
    h = b.length;
    if (b && h) {
        for (var d = 0,
        n = ""; d < h; d++) {
            n = b[d];
            if (n.src.indexOf(m.FileName) > -1 || n.src.indexOf("pc.min.js") > -1) {
                m.cacheInfo.jsDom = n;
                k = n.src.substring(n.src.lastIndexOf("/") + 1, n.src.length);
                break
            }
        }
    } else {
        return
    }
    if (k && k.length) {
        l = k.indexOf("?");
        g = k.substring(l + 1, k.length);
        c = g.split("&");
        f = c.length;
        for (var d = 0,
        n = "",
        j, a; d < f; d++) {
            n = c[d].split("=");
            j = n[0];
            a = n[1];
            e[j] = a
        }
    }
    for (var j in e) {
        if (!m.cacheInfo.urlParamList[j]) {
            m.cacheInfo.urlParamList[j] = e[j]
        }
    }
    return e
};
ZC.pc.common.extend = function(d, c, a) {
    if (d && typeof d === "object" && c && typeof c === "object") {
        for (var b in c) {
            if (c.hasOwnProperty(b) && (!d.hasOwnProperty(b) || a)) {
                d[b] = c[b]
            }
        }
        return d
    }
    return
};
ZC.pc.namespace("ZC.pc.common.dom");
ZC.pc.common.dom.find = function(l, m) {
    var f = "",
    h = [],
    c = "*",
    g = [],
    a = "";
    m = m || document;
    if ( - 1 < l.indexOf("#")) {
        l = l.replace("#", "");
        f = m.getElementById(l)
    } else {
        if ( - 1 < l.indexOf(".")) {
            if (0 !== l.indexOf(".")) {
                g = l.split(".");
                c = g[0];
                l = g[1]
            } else {
                l = l.replace(".", "")
            }
            a = m.getElementsByTagName(c);
            for (var e = 0,
            b = a.length,
            n = "",
            k = 0; e < b; e++) {
                n = a[e].className.split(" ");
                k = n.length;
                for (var d = 0; d < k; d++) {
                    if (n[d] == l) {
                        h.push(a[e]);
                        break
                    }
                }
            }
            f = h
        } else {
            f = m.getElementsByTagName(l)
        }
    }
    return f
};
ZC.pc.common.dom.getTargetObjList = function(d, a) {
    var c = ZC.pc,
    a = a && typeof a === "object" ? a: window,
    d = d ? d: c.config.targetList,
    e = {};
    for (var b in d) {
        e[b] = c.common.dom.find(d[b], a.document)
    }
    return e
};
ZC.pc.common.dom.append = function(b, c) {
    var e = ZC.pc,
    c = c || document.body,
    f = c.innerHTML,
    d = document.createDocumentFragment(),
    a = d.appendChild(document.createElement("div"));
    a.innerHTML = b;
    a = d.lastChild;
    d.removeChild(a);
    c.appendChild(a.childNodes[0])
};
ZC.pc.namespace("ZC.pc.common.event");
ZC.pc.common.event.bindTargetEvent = function(b, a, d, e) {
    var c = ZC;
    if (typeof b !== "object") {
        b = c.common.dom.find(b)
    }
    if (d && typeof d == "function") {
        var e = d;
        if (window.addEventListener) {
            b.addEventListener(a, e, false)
        } else {
            if (window.attachEvent) {
                b.attachEvent("on" + a, e)
            }
        }
    }
};
ZC.pc.common.event.stopEvent = function(a) {
    if (a && a.preventDefault) {
        a.preventDefault()
    } else {
        window.event.returnValue = false
    }
};
ZC.pc.common.setCookie = function(c, f, b) {
    var e = ZC.pc,
    c = e.cacheInfo.urlParamList.sysNum + "_" + c,
    d = new Date(),
    a = "";
    d.setTime(d.getTime() + 24 * 3600 * 1000);
    a = d.toGMTString();
    document.cookie = c + "=" + f + ";expires=" + a
};
ZC.pc.common.getCookie = function(c) {
    var h = ZC.pc,
    c = h.cacheInfo.urlParamList.sysNum + "_" + c,
    a = document.cookie,
    e = a.split("; "),
    d = e.length;
    for (var b = 0,
    g = "",
    f = []; b < d; b++) {
        g = e[b];
        f = g.split("=");
        if (c === f[0]) {
            return f[1]
        }
    }
    return false
};
ZC.pc.namespace("ZC.pc.pin");
ZC.pc.namespace("ZC.pc.pin.classLan");
ZC.pc.pin.classLan = {
    transition_base: "-moz-transition: all .3s ease-in-out .1s; -webkit-transition: all .3s ease-in-out .1s; -o-transition: all .3s ease-in-out .1s; -ms-transition: all .3s ease-in-out .1s; transition: all .3s ease-in-out .1s;",
    pin_base: "position: fixed; z-index: 2147483583; box-sizing: content-box; {0} {1}",
    pin_base_12: "height: 43px; bottom: -43px; padding: 0 20px; border-radius: 4px 4px 0 0; {0}",
    pin_effect_in_12: {
        bottom: 0
    },
    pin_effect_out_12: {
        bottom: "-43px"
    },
    pin_base_34: "top: 50%; margin-top: -104px; width: 40px; {0}",
    pin_a_base: "text-decoration: none; outline: none; font-family: Microsoft Yahei, Arial, Helvetica; color: #fff; font-size: 16px; display: inline-block; margin: 0; padding: 0; border: none; {0}",
    pin_a_base_12: "line-height:43px; float:none;",
    pin_a_base_34: "height: 100%; width: 100%;",
    pin_img_base: "vertical-align: middle; width: 25px; border:none; {0}",
    pin_img_base_12: "margin: 2px 8px 0 0; float: none; position: static; height: 21px;",
    pin_img_base_34: "margin: 15px 0px 15px 4px;",
    pin_span_base: "font-family: Microsoft Yahei, Arial, Helvetica; color: #fff; font-size:16px; margin: 0; padding: 0; {0}",
    pin_span_base_12: "",
    pin_span_base_34: "margin-left: 10px; margin-bottom: 20px; display: inline-block; width: 1em; word-break: break-all; white-space: pre-wrap;",
    pin_count_base: "height: 30px; width: 30px; line-height: 30px; border: 1px solid #FFFFFF; border-radius: 50%; background-color: red; color: #FFFFFF; text-align: center; display: none; position: absolute; {0}",
    pin_count_base_1: "right: -15px; top: -15px;",
    pin_count_base_2: "right: -15px; top: -15px",
    pin_count_base_3: "bottom: -15px; right: -15px;",
    pin_count_base_4: "bottom: -15px; left: -15px;",
    iframeBox_base: "width: 320px; height: 480px; position: fixed; bottom: -500px; border-radius: 5px 5px 0 0; overflow: hidden; z-index: 2147483583; box-shadow: rgba(0,0,0,0.1) 0px 0px 5px 2px;border:1px solid #ccc\\9; {0} {1}"
};
ZC.pc.pin.createPin = function() {
    var g = ZC.pc,
    h = {},
    a = g.cacheInfo.customConfig,
    f = "",
    d = "",
    c = "",
    b = "",
    e = "";
    g.pin.pinStyle = d = g.pin.getPinStyle();
    h = g.updateTargetStrList();
    f = '<div id="' + h.zhichiBtnBox + '" style="' + d.pin + '"><a hideFocus id="' + h.zhichiBtn + '" href="javascript: void(0);" style="' + d.pin_a + '"><img style="' + d.pin_img + '" src="' + g.cacheInfo.host.name + '/chat/pc/img/zhichichatBtnBg1.png"><span style="' + d.pin_span + '">' + a.title + '</span></a><span id="' + h.zhichiCount + '" style="' + d.pin_count + '"></span></div>';
    g.common.dom.append(f);
    g.updateTargetObjList();
    g.pin.effectManip(true)
};
ZC.pc.pin.getPinStyle = function() {
    var p = ZC.pc,
    j = p.pin.classLan,
    n = p.cacheInfo.customConfig,
    d = "",
    c = "",
    k = "",
    o = "",
    m = "",
    l = "",
    i = "",
    h = "",
    f = "",
    e = "",
    b = "",
    a = "",
    g = "";
    switch (n.margin) {
    case 1:
        d = "border-radius: 4px 4px 0px 0px; right: 100px; background: " + n.color + ";";
        c = j.pin_base_12;
        o = j.pin_a_base_12;
        l = j.pin_img_base_12;
        h = j.pin_span_base_12;
        e = j.pin_count_base_1;
        a = j.pin_effect_in_12;
        g = j.pin_effect_out_12;
        break;
    case 2:
        d = "border-radius: 4px 4px 0px 0px; left: 50px; background: " + n.color + ";";
        c = j.pin_base_12;
        o = j.pin_a_base_12;
        l = j.pin_img_base_12;
        h = j.pin_span_base_12;
        e = j.pin_count_base_2;
        a = j.pin_effect_in_12;
        g = j.pin_effect_out_12;
        break;
    case 3:
        d = "border-radius: 0px 5px 5px 0px; left: -40px; background: " + n.color + ";";
        c = j.pin_base_34;
        o = j.pin_a_base_34;
        l = j.pin_img_base_34;
        h = j.pin_span_base_34;
        e = j.pin_count_base_3;
        a = {
            left: 0
        };
        g = {
            left: "-40px"
        };
        break;
    case 4:
        d = "border-radius: 5px 0px 0px 5px; right: -40px; background: " + n.color + ";";
        c = j.pin_base_34;
        o = j.pin_a_base_34;
        l = j.pin_img_base_34;
        h = j.pin_span_base_34;
        e = j.pin_count_base_4;
        a = {
            right: 0
        };
        g = {
            right: "-40px"
        };
        break
    }
    k = p.common.formatLan(j.pin_base, [p.common.formatLan(c, [d]), j.transition_base]);
    m = p.common.formatLan(j.pin_a_base, [o]);
    i = p.common.formatLan(j.pin_img_base, [l]);
    f = p.common.formatLan(j.pin_span_base, [h]);
    b = p.common.formatLan(j.pin_count_base, [e]);
    return {
        pin: k,
        pin_a: m,
        pin_img: i,
        pin_span: f,
        pin_count: b,
        effectIn: a,
        effectOut: g
    }
};
ZC.pc.pin.effectManip = function(c, e) {
    var d = ZC.pc,
    b = d.cacheInfo.targetObjList.zhichiBtnBox,
    a = d.pin.pinStyle,
    e = e || 500;
    d.cssEffectManip(b, a, c, e)
};
ZC.pc.namespace("ZC.pc.iframe");
ZC.pc.iframe.createIframeBox = function() {
    var d = ZC.pc,
    e = {},
    c = "",
    b = "",
    a = d.cacheInfo.host.urlList.pcIframe;
    d.iframe.iframeBoxStyle = b = d.iframe.getIframeBoxStyle();
    e = d.updateTargetStrList();
    if (d.cacheFlag.isEnableVisit && d.cacheInfo.aid && d.cacheInfo.visitId) {
        a = a + "#aid=" + d.cacheInfo.aid + "#visitId=" + d.cacheInfo.visitId
    }
    c = '<div id="' + e.zhichiIframeBox + '" style="' + b.iframeBox + '"><iframe id="' + e.zhichiIframe + '" src="' + a + '" name="' + e.zhichiIframe + '" frameborder="no" scrolling="no" style="width: 100%; height: 100%; border: 0;"></iframe></div>';
    d.common.dom.append(c);
    d.common.event.bindTargetEvent(window, "message",
    function(h) {
        var g = h.data,
        f, i = d.common.dom.getTargetObjList();
        d.cacheFlag.isReinit = true;
        if (/^unreadCount\-\d+$/.test(g)) {
            d.cacheFlag.isReinit = false;
            f = (g.split("-"))[1] > 99 ? "99+": (g.split("-"))[1]
        } else {
            if ("zhichiMin" == g) {
                d.cacheFlag.isReinit = false;
                d.common.setCookie("s", 0)
            } else {
                if ("zhichiClose" == g) {
                    d.common.setCookie("s", 0)
                }
            }
        }
        if (g.indexOf("zhichi") < 0) {
            return
        }
        d.pin.effectManip(true);
        d.iframe.effectManip(false, 0);
        d.common.event.stopEvent(h)
    })
};
ZC.pc.iframe.getIframeBoxStyle = function() {
    var f = ZC.pc,
    c = f.cacheInfo.customConfig,
    b = f.pin.classLan,
    e = "",
    d = b.iframeBox_base,
    a = "";
    switch (c.margin) {
    case 1:
        e = "right: 50px;";
        break;
    case 2:
        e = "left: 50px;";
        break;
    case 3:
        e = "left: 50px;";
        break;
    case 4:
        e = "right: 50px;";
        break
    }
    a = f.common.formatLan(d, [e, b.transition_base]);
    return {
        iframeBox: a,
        effectIn: {
            bottom: 0
        },
        effectOut: {
            bottom: "-500px"
        }
    }
};
ZC.pc.iframe.effectManip = function(c, e) {
    var d = ZC.pc,
    b = d.cacheInfo.targetObjList.zhichiIframeBox,
    a = d.iframe.iframeBoxStyle,
    e = e || 500;
    d.cssEffectManip(b, a, c, e);
    d.cacheFlag.isShowedIframe = c
};
document.onready = (function() {
    ZC.pc.init()
})();