/* This source code is Copyright (c) Vibrant Media 2001-2008 and forms part of the patented Vibrant Media product "IntelliTXT" (sm). */
try {if('undefined'==typeof $iTXT) {var $iTXT={};} if('undefined'==typeof $iTXT.edit) {$iTXT.edit={};}} catch(x) {} var tintColor="";var sUM=true;var sDM=false;var scID=null;function cHTR(hexStr){var rgb=new Object();rgb.r=parseInt(hexStr.substr(1,2),16);rgb.g=parseInt(hexStr.substr(3,2),16);rgb.b=parseInt(hexStr.substr(5,2),16) ; return rgb;} function cRTH(r,g,b){var rFill=(r<10)?"0":"";var gFill=(g<10)?"0":"";var bFill=(b<10)?"0":"";return"#"+rFill+r.toString(16)+gFill+g.toString(16)+bFill+b.toString(16);} function getTint(baseColor,pctTint){var rgb=cHTR(baseColor);var diffR=parseInt('FF',16)-rgb.r;var diffG=parseInt('FF',16)-rgb.g;var diffB=parseInt('FF',16)-rgb.b;var pctWhite=(100-pctTint)/100;var finalR=Math.floor(rgb.r+(diffR*pctWhite));var finalG=Math.floor(rgb.g+(diffG*pctWhite));var finalB=Math.floor(rgb.b+(diffB*pctWhite));var thisTint=cRTH(finalR,finalG,finalB);return thisTint;} function coBl(oN,pN,sC,eC,blS,blT) {if('undefined'==typeof sC||''==sC) {sC='#ffffff';} var oR=document.getElementById(oN);if(typeof oR.cBID=="number")kCB(oN);var t=blS*1000;var s=30*blS;var sCS=cHTR(sC);var eCS=cHTR(eC);var rAr=new Array();var gAr=new Array();var bAr=new Array();var rInc=(eCS.r-sCS.r)/s;for(x=0;x<=s;x++){rAr.push(Math.round(sCS.r+(x*rInc)));} var gInc=(eCS.g-sCS.g)/s;for(x=0;x<=s;x++){gAr.push(Math.round(sCS.g+(x*gInc)));} var bInc=(eCS.b-sCS.b)/s;for(x=0;x<=s;x++){bAr.push(Math.round(sCS.b+(x*bInc)));} oR.blSt=0;oR.nS=oN;oR.pS=pN;var blInt=t/s;oR.cBID=setInterval(blFn,blInt);var slR=oR;function blFn(){slR.blSt++;if(slR.blSt<=s){var tC=(isNaN(rAr[slR.blSt])||isNaN(gAr[slR.blSt])||isNaN(bAr[slR.blSt]))?eC:cRTH(rAr[slR.blSt],gAr[slR.blSt],bAr[slR.blSt]);if(blT=="ie6"){var fS="progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#ffffff,endColorStr="+tC+")";cSt(slR.nS,'filter',fS);}else{cSt(slR.nS,oR.pS,tC);}}else{var tC=eC;if(blT=="ie6"){var fS="progid:DXImageTransform.Microsoft.Gradient(gradientType=0,startColorStr=#ffffff,endColorStr="+tC+")";cSt(slR.nS,'filter',fS);}else{cSt(slR.nS,oR.pS,tC);} clearInterval(slR.cBID);}}};function kCB(oN){var oR=document.getElementById(oN);if(typeof oR.cBID=="number")clearInterval(oR.cBID);};function eROn(e,n,col){var tRe="sResult"+n;var tTt="srTitle"+n;if(ieCA==n) return;tintColor=getTint(col,30);eROf(e,ieCA,tintColor);ieCA=n;var onColor=tintColor;var curcolor=document.getElementById(tRe).style.backgroundColor;kCB(tRe);cSt(tRe,"display","block");if(navigator.userAgent.indexOf("MSIE 6.0")!=-1||navigator.userAgent.indexOf("MSIE 5.5")!=-1){var curFilter=document.getElementById(tRe).style['filter'];if(curFilter==""){curcolor="#FFFFFF";}else{var sI=curFilter.lastIndexOf("#");curcolor=curFilter.substr(sI,7);} coBl(tRe,'backgroundColor',curcolor,onColor,.25,'ie6');}else{cSt(tRe,'backgroundImage','url('+gImgLoc+'siteresults/bkg_grad_white.png)');if(curcolor.indexOf('rgb')!=-1){mozColor=curcolor.substring(4,curcolor.length-1);var cSplit=mozColor.split(",");curcolor=cRTH(parseInt(cSplit[0]),parseInt(cSplit[1]),parseInt(cSplit[2]));} coBl(tRe,'backgroundColor',curcolor,onColor,.25);cSt(tRe,'cursor','pointer');} cSt(tTt,'textDecoration','underline');} function eROf(e,n,col){var offColor="#ffffff";if(n<0) {offColor=col;n=0;} var tRe="sResult"+n;var tTt="srTitle"+n;var oRes=document.getElementById(tRe);if(null==oRes) {return;} var oBB=cBB(oRes);var posx=0,posy=0;if(!e)var e=window.event;if(e) {if(e.pageX||e.pageY) {posx=e.pageX;posy=e.pageY;} else if(e.clientX||e.clientY) {posx=e.clientX+document.body.scrollLeft+document.documentElement.scrollLeft;posy=e.clientY+document.body.scrollTop+document.documentElement.scrollTop;} var eT=(e.target)?e.target:e.srcElement;if(tRe==eT.id&&posx>=oBB.l&&posx<=oBB.r&&posy>=oBB.t&&posy<=oBB.b) {return;}} var curcolor=document.getElementById(tRe).style.backgroundColor;kCB(tRe);if(navigator.userAgent.indexOf("MSIE 6.0")!=-1||navigator.userAgent.indexOf("MSIE 5.5")!=-1){var curFilter=document.getElementById(tRe).style['filter'];if(curFilter==""){curcolor="#FFFFFF";}else{var sI=curFilter.lastIndexOf("#");curcolor=curFilter.substr(sI,7);} coBl(tRe,'backgroundColor',curcolor,offColor,.25,'ie6');}else{if(curcolor.indexOf('rgb')!=-1){mozColor=curcolor.substring(4,curcolor.length-1);var cSplit=mozColor.split(",");curcolor=cRTH(parseInt(cSplit[0]),parseInt(cSplit[1]),parseInt(cSplit[2]));} coBl(tRe,'backgroundColor',curcolor,offColor,.25);cSt(tRe,'cursor','pointer');} cSt(tTt,'textDecoration','none');} function scD(){if(typeof scID=="number")clearInterval(scID);var resultsDIV=getTID('iTt','results','div'),resultsWND=getTID('iTt','resultWindow','div');function i_sUp(){var thisY=(resultsDIV.style['top']=="")?0:parseInt(resultsDIV.style['top']);var topMax=-(resultsDIV.offsetHeight-resultsWND.offsetHeight);document.getElementById('butUpArrow').src=gImgLoc+'siteresults/icon_up.gif';document.getElementById('butUp').style['borderColor']="#4b4b4b";resultsDIV.style['top']=(thisY-5)+"px";if(parseInt(resultsDIV.style['top'])<topMax){scSt();resultsDIV.style['top']=topMax+"px";document.getElementById('butDownArrow').src=gImgLoc+'siteresults/icon_down_off.gif';document.getElementById('butDown').style['borderColor']="#cccccc";document.getElementById('butDown').style['backgroundColor']="#ffffff";sDM=true;}else{document.getElementById('butDown').style['backgroundColor']=tintColor;sDM=false;}} scID=setInterval(i_sUp,20);} function scU(){if(typeof scID=="number")clearInterval(scID);var resultsDIV=getTID('iTt','results','div');function i_sUp(){var thisY=(resultsDIV.style['top']=="")?0:parseInt(resultsDIV.style['top']);var bMx=0;document.getElementById('butDownArrow').src=gImgLoc+'siteresults/icon_down.gif';document.getElementById('butDown').style['borderColor']="#4b4b4b";resultsDIV.style['top']=(thisY+5)+"px";if(parseInt(resultsDIV.style['top'])>bMx){scSt();resultsDIV.style['top']=bMx+"px";document.getElementById('butUpArrow').src=gImgLoc+'siteresults/icon_up_off.gif';document.getElementById('butUp').style['borderColor']="#cccccc";document.getElementById('butUp').style['backgroundColor']="#ffffff";sUM=true;}else{document.getElementById('butUp').style['backgroundColor']=tintColor;sUM=false;}} scID=setInterval(i_sUp,20);} function scSt(){clearInterval(scID);if(sDM!=true) document.getElementById('butDown').style['backgroundColor']="#ffffff";if(sUM!=true) document.getElementById('butUp').style['backgroundColor']="#ffffff";} function inSc(){var resultsDIV=getTID('iTt','results','div'),resultsWND=getTID('iTt','resultWindow','div');if(resultsDIV) if(resultsDIV.offsetHeight<=resultsWND.offsetHeight){if(resultsDIV.offsetHeight==0&&resultsWND.offsetHeight==0)return;document.getElementById('butUp').style['display']="none";document.getElementById('butDown').style['display']="none";var ttbx=document.getElementById('tooltipbox');sSWH(gCW(ttbx),gCH(ttbx));}} ; 