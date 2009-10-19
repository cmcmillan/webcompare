String.prototype.ltrim = function() {
	return this.replace(/^\s+/,"");
}


ch_ad_url = '';
ch_oeh = window.onerror;
ch_chitika_loaded = true;
ch_loaded = 0;
ch_meta_vars = new Array('codev','lat','lon');


function dq(s) { return (s != null) ? '"' + s + '"' : '""'; }
function ch_au(p,v) { if (v) { window.ch_ad_url += '&' + p + '=' + v; } }
function ch_aue(p,v) { if (v) { ch_au(p,escape(v)); } }
function ch_def(v, def) { return (v) ? v : def; }

function ch_ad_render_ecpm() {
  var w = window;
  if(typeof(w["ch_mmhtml"])!="undefined") {
    var thehtml = w.ch_mmhtml["output"];
    if (thehtml && thehtml.indexOf("<ROLLUP>") == -1) {
      ch_decision(true);
      return;
    }
  }
  ch_decision(false);
}

function ch_ad_render_search() {
  var w = window;
  if(typeof(w["ch_mmhtml"])!="undefined") {
    var thehtml = w.ch_mmhtml["output"];
    if (thehtml && thehtml.indexOf("overture") != -1) {
      ch_decision(true);
      return;
    }
  }
  ch_decision(false);
}

function ch_get_style(x, styleProp) {
  if (x.currentStyle) {
    return x.currentStyle[styleProp];
  } else if (window.getComputedStyle) {
    return document.defaultView.getComputedStyle(x,null).getPropertyValue(styleProp);
  }
}

function append_func(o, a) {
  return function (e) {
    if (typeof(o) == "function") { o(e); }
    return a(e);
  };
}

function ch_write_iframe(f, thehtml, r, width, height) {
  var w = window;
  var fobj = document.createElement("iframe");
  fobj.src = "about:blank";
  try {fobj.contentWindow.document.designMode = "on";} catch (e) {}
  try {fobj.contentEditable = true;} catch (e) {}
  fobj.border = "0";
  fobj.style.margin = fobj.style.padding = fobj.style.border= 0;
  fobj.padding = "0";
  fobj.frameBorder = 0;
  fobj.marginWidth = 0;
  fobj.marginHeight = 0;
  fobj.vspace = 0;
  fobj.hspace = 0;
  fobj.allowTransparency = true;
  fobj.scrolling = "no";
  var tries = 0;
  var interval;

  var checkDisplay = function() {
    if (tries++ > 70) {
      w.clearInterval(interval);
    }
    var p = fobj;
    noDisplayNone = true;
    while (p != null) {
      try {
        st = ch_get_style(p, "display");
        if (st == "none") {
          noDisplayNone = false;
          break;
        }
      } catch(e) {}
      p = p.parentNode;
    }
    p = f.parentNode;
    if (noDisplayNone) {
      w.clearInterval(interval);
      var d = w.ch_dim["ch_ad"+r];
      if (width && height) {
          fobj.width = width;
          fobj.height = height;
      } else {
          fobj.width = d[0];fobj.height = d[1];
      }
      f.parentNode.insertBefore(fobj,f);
      var fdoc = fobj.contentWindow.document;
      fdoc.open();
      fdoc.write(thehtml);
      fdoc.close();
    }
  }
  interval = w.setInterval(checkDisplay, 100);
}

function ch_decision(render) {
  var w = window;
  var r = w.ch_mmhtml["cb"];
  var thehtml = w.ch_mmhtml["output"];
  var f = document.getElementById("ch_ad"+r);
  if (typeof(f) == "undefined") {return;}
  if (thehtml && render) {
    ch_write_iframe(f, thehtml, r, null, null);
  } else {
    f.style.display = "none";
    ch_chitika_loaded = false;
    if (w.ch_mmhtml["pixelhtml"]) {
        ch_write_iframe(f, "<html><body>" + w.ch_mmhtml["pixelhtml"] + "</body></html>", r, 1, 1);
    }
    w.ch_default_render_fallback(r);
  }
}

function ch_default_render_fallback(r) {
  var w = window;
  var d = document;
  if (w["ch_render_fallback"]) {
    return w.ch_render_fallback(r);
  }
  var ow,owl,di,dobj,content,s;
  ow = document.write;
  owl = document.writeln;
  var f = d.getElementById("ch_ad"+r);
  di = w.ch_dim["ch_ad"+r];
  if (typeof(di[2]) == "undefined") {
    di[2] = function () {};
  }
  w.ch_alternate_ad_js = di[3];
  w.ch_alternate_ad_html = di[4];
  w.ch_alternate_ad_blank = di[5];

  if (!w.ch_alternate_ad_js && !w.ch_alternate_ad_html && !d[2] && !w.ch_alternate_ad_blank) {
    return;
  }
  dobj = d.createElement("div");
  if (f) {
    f.parentNode.insertBefore(dobj,f);
  }
  var dio = function () {
    if (f) {
      d.write = function (c) {dobj.innerHTML += c;};
      d.writeln = function (c) {d.write(c+"\n");};
    }
  };
  var dif = function () {
    d.write = ow;
    d.writeln = owl;
  };
  var load;
  if (w.ch_alternate_ad_js) {
    if (f) {
      load = function (e) {
        dio();
        di[2]();
        s=d.createElement("script");
        s.type = "text/javascript";
        s.src = w.ch_alternate_ad_js;
        f.parentNode.insertBefore(s,f);
      };
    } else {
      load = function (e) {
        dio();
        di[2]();
        d.write(unescape("%3Cscript%20type%3D%22text/javascript%22%20src%3D%22"+escape(w.ch_alternate_ad_js)+"%22%3E%3C/script%3E"));
      };
    }
  } else if (w.ch_alternate_ad_html) {
    load = function (e) {
      dio();
      di[2]();
      d.write(w.ch_alternate_ad_html);
      dif();
    };
  } else if (w.ch_alternate_ad_blank) {
    load = function (e) {
      dio();
      di[2]();
      d.write(unescape("%3Cdiv%20style%3D%22width%3A%20"+d[0]+"px%3Bheight%3A%20"+d[1]+"px%3Bborder%3A0%3Bmargin%3A0%3B%22%3E%3C/div%3E"));
      dif();
    };
  } else {
    load = function (e) {
      dio();
      di[2]();
    }
  }
  if (!ch_loaded && f) {
    w.onload = append_func(w.onload, load);
    dif();
  } else {
    load(0);
    if (!f) {
      dif();
    }
  }
}

function readCookie(name) {
    var nameEQ = name + "=";
    var ca = document.cookie.split(";");
    for(var i=0;i < ca.length;i++) {
        var c = ca[i];
        //while (c.charAt(0)==" ") c = c.substring(1,c.length);
        c = c.ltrim();
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return "";
}

function append_domain(w){
    var domain = window.location.hostname;
    if (domain != null){
        var ar1 = /([^\.]+)\.(com|net|org|info|mobi|co\.uk|org\.uk|ac\.uk|uk)$/.exec(domain);
        if (ar1 && ar1[1]){
            w.ch_client = w.ch_client + '_' + ar1[1];
        }
    }
}

function ch_mm() {
  var w = window;

  w.ch_SAF = navigator.userAgent.toLowerCase().indexOf("safari") != -1;
  w.ch_referrer = document.referrer;

  var m = String(window.location.href).match(/#adpro-(.+?)_(.+)$/);
  if (m) {
    var wi = ch_def(w['ch_width'],'728');
    var he = ch_def(w['ch_height'],'90');
    var f = wi+'x'+he;
    var s = ch_def(w['ch_sid'], '-').toLowerCase();
    if (m[1] == f && unescape(m[2].toLowerCase()) == s) {
      document.write(unescape("%3Ciframe%20name%3D%22adprotest%22%20width%3D%22"+wi+"%22%20height%3D%22"+he+"%22%20frameborder%3D%220%22%20src%3D%22http%3A//scripts.chitika.net/static/adpro.html%22%20marginwidth%3D%220%22%20marginheight%3D%220%22%20vspace%3D%220%22%20hspace%3D%220%22%20allowtransparency%3D%22true%22%20scrolling%3D%22no%22%3E%3C/iframe%3E"));
      return;
    }
  }

  var m = String(window.location.href).match(/#chitikatest=(.+)/);
  w.ip = false;
  if (m) {
    w.ip = 'us';
    w.ch_referrer = "http://www.google.com/search?q="+m[1];
  } else if (String(window.location.href).match(/#chitikatest/)) {
    w.ip = 'us';
    w.ch_referrer = "http://www.google.com/search?q=mortgage";
  }
  if (w.ch_non_contextual == "4" && w.ch_vertical == "premium" && !w.ch_alternate_ad_url && !w.ch_backfill){
      w.ch_search_referral = 1;
  }

  if (w.ch_search_referral && typeof(w.ch_behavior_window) == "undefined")
    w.ch_behavior_window = 1800;

  w.onerror = w.ch_oeh;
  var amm_host = ch_def(w.ch_host, "mm.chitika.net");

  if (w.ch_search_referral || w.ch_ecpm_i_want) {
    amm_host = ch_def(w.ch_host, "mm.chitika.net");
  }
  w.ch_ad_url = 'http://' + amm_host + '/minimall?w=' + w.ch_width + '&h=' + ch_height;

  if (w.ch_append_tracking){ w.ch_client = w.ch_client + "_" + w.ch_append_tracking; }
  else if (w.ch_client == 'epodunk') { append_domain(w); }

  ch_aue('client', w.ch_client);
  ch_aue('accountid', w.ch_accountid);
  ch_aue('noctxt', w.ch_non_contextual);
  ch_aue('partner', w.ch_partner);
  ch_aue('sid', w.ch_sid);
  ch_au('provider', w.ch_provider);
  ch_aue('url', w.ch_pu);
  ch_aue('ref', w.ch_referrer);
  ch_aue('query', w.ch_query);
  ch_au('ip', w.ip);
  if (w.ip) {
    ch_au('test', '1');
  }
  if (w.ch_type) {
    ch_au('type', w.ch_type);
    if (w.ch_queries && w.ch_queries.constructor.toString().indexOf("Array") != -1) {
      ch_aue('mquery', w.ch_queries.join('|'));
    } else if (w.ch_query) {
      ch_aue('mquery', w.ch_query);
    }
  }
  ch_aue('nobanners', w.ch_no_banners);
  if (w.ch_adpro_button) {
    ch_au('target_cookie', 'grfrzjvIfB');
  }
  ch_aue('tptracker', w.ch_third_party_tracker);
  ch_aue('defaulttab', w.ch_default_tab);
  ch_aue('defaultcat', w.ch_default_category);
  ch_aue('filtercat', w.ch_filter_category);
  ch_aue('filterin', w.ch_filter_in);
  ch_aue('filterout', w.ch_filter_out);
  ch_aue('cttarget', w.ch_target);
  ch_aue('att', w.ch_att);
  ch_aue('nosearch', w.ch_nosearch);
  ch_aue('searchref', w.ch_search_referral); 
  ch_aue('noprice', w.ch_noprice);
  ch_aue('noborders', w.ch_noborders);
  ch_aue('backfill',w.ch_backfill);
  ch_aue('vertical', w.ch_vertical);
  ch_aue('cl_border', w.ch_color_border);
  ch_aue('cl_bg', w.ch_color_bg);
  ch_aue('cl_title', w.ch_color_title);
  ch_aue('cl_text', w.ch_color_text);
  ch_aue('cl_site_link', w.ch_color_site_link);
  ch_aue('fn_title', w.ch_font_title);
  ch_aue('fn_text', w.ch_font_text);
  ch_aue('alturl', w.ch_alternate_ad_url);
  ch_aue('altcss', w.ch_alternate_css_url);
  ch_aue('ecpmiwant', w.ch_ecpm_i_want);
  ch_aue('udq', w.ch_udq);
  ch_aue('behavioral_window', w.ch_behavioral_window);
  ch_aue('previous_format',w.ch_previous_format);
  ch_aue('premium_search',w.ch_premium_search);
  ch_aue('tab_click',w.ch_tab_click);
  ch_aue('prefill_search',w.ch_prefill_search);
  ch_aue('tp',w.ch_tp);
  ch_aue('must_fill',w.ch_must_fill);
  ch_aue('target_pixel',w.ch_target_pixel);

  for (var i in ch_meta_vars){
    var k = "ch_" + ch_meta_vars[i];
    if (typeof(w[k]) != "undefined"){
        ch_aue(ch_meta_vars[i],(w[k]));
    }
  }

  if (w.ch_demo_mode == 1) {
    ch_au('ip', '71.248.173.210');
    ch_au('demomode', '1');
  }

  var r = Math.round(Math.random() * 1000);
  ch_au('cb', r);

  w.ch_ad_url = w.ch_ad_url.substring(0, 2048);
  w.ch_ad_url = w.ch_ad_url.replace(/%\w?$/, '');

  if (typeof(w.ch_dim) == "undefined") {
    w.ch_dim = {};
  }
  w.ch_dim["ch_ad"+r] = [w.ch_width, w.ch_height, w.ch_alternate_js_callback, w.ch_alternate_ad_js, w.ch_alternate_ad_html, w.ch_alternate_ad_blank];

  if (w.ch_search_referral) {
    if (!w.ch_allow_pixel && (w.ch_alternate_ad_js || w.ch_alternate_js_callback) && !String(w.ch_referrer).match(/(google|search.yahoo|search.msn|search.live|ask|search.aol|bing).com/)) {
      ch_clear();
      return ch_default_render_fallback(r);
    }
    w.onload = append_func(w.onload, function (e) {window.ch_loaded = 1;});
    ch_aue("required_text", "overture");
  }

  if (w.ch_previous_format){
    w.ch_previous_format = w.ch_previous_format + "," + w.ch_width + "x" + w.ch_height;
  }
  else{
     w.ch_previous_format = w.ch_width + "x" + w.ch_height;
  }

  if (w.ch_ecpm_i_want || w.ch_search_referral) {
    w.ch_ad_url += "&output=simplejs&callback=" + (w.ch_ecpm_i_want ? "ch_ad_render_ecpm" : "ch_ad_render_search");
    w.ch_width = w.ch_height = 0;
    var s=document.createElement("script");
    s.type = "text/javascript";
    s.src = w.ch_ad_url;
    document.getElementsByTagName("head")[0].appendChild(s);
    w.ch_ad_url = "about:blank";
  }
  document.write('<ifr' + 'ame' + ' id="ch_ad'+r+'" name="ch_ad'+r+'"' + ' width=' + dq(w.ch_width) + ' height=' + dq(w.ch_height) + ' frameborder="0"' + ' src=' + dq(w.ch_ad_url) + ' marginwidth="0"' + ' marginheight="0"' + ' vspace="0"' + ' hspace="0"' + ' allowtransparency="true"' + ' scrolling="no">' + '</ifr' + 'ame>');
  ch_clear();
}

function ch_eh(m,u,l) {
  ch_mm();
  return true;
}

function ch_clear() {
  var w = window;
  w.ch_pu = null;
  w.ch_ad_url = null;
  w.ch_query = null;
  w.ch_type = null;
  w.ch_alternate_css_url = null;
  w.ch_alternate_ad_url = null;
  w.ch_sid = null;
  w.ch_nosearch = null;
  w.ch_noprice = null;
  w.ch_noborders = null;
  w.ch_backfill = null;
  w.ch_default_tab = null;
  w.ch_default_category = null;
  w.ch_filter_category = null;
  w.ch_vertical = null;
  w.ch_ecpm_i_want = null;
  w.ch_search_referral = null;
  w.ch_filter_in = null;
  w.ch_filter_out = null;
  w.ch_udq = null;
  w.ch_behavioral_window = null;
  w.ch_adpro_button = null;
  w.ch_post = null;
  w.ch_premium_search = null;
  w.ch_tab_click = null;
  w.prefill_search = null;
  w.ch_append_tracking = null;
  w.ch_tp = null;
  w.ch_must_fill = null;
  w.ch_target_pixel = null;

  for (var i in ch_meta_vars){
    var k = "ch_" + ch_meta_vars[i];
    if (typeof(w[k]) != "undefined"){
        w[k] = null;
    }
  }      
}

window.onerror = ch_eh;

if (window.ch_pu == null) {
    ch_pu = "" + document.location;
    if (window.ch_post != null){
        var post = document.getElementById(window.ch_post);
        if (post == null){ /* try reading the name? */ }
        else{
            if (post.value != null){    post = post.value;      }
            else{                       post = post.innerHTML;  }
        }
        if (post != null){
            post = post.replace(/\n/g,',').replace(/\s/g,' ');
            if (ch_pu.indexOf("?") >= 0){
                ch_pu = ch_pu + '&' + window.ch_post + '=' + escape(post);
            }
            else{
                ch_pu = ch_pu + '?' + escape(post);
            }
        }
    }
} else {
  ch_loc = document.location;
}

ch_mm();
