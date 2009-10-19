/* This source code is Copyright (c) Vibrant Media 2001-2009 and forms part of the patented Vibrant Media product "IntelliTXT" (sm). */ 
$iTXT.js.loader["$iTXT.ui.ComponentBase"]=true;$iTXT.ui.ComponentBase_Load=function(){var undefined;$iTXT.ui.ComponentBase=$iTXT.core.Class.create({init:function(_options)
{this.options=$iTXT.core.Util.extend({},_options);},getHTML:function()
{if(this.rootElement)
{return $iTXT.core.Builder.make("DIV",{},[this.rootElement]).innerHTML;}
return"";}});}
$iTXT.js.loader["$iTXT.ui.TooltipSlideOut"]=true;$iTXT.ui.TooltipSlideOut_Load=function(){var undefined;$iTXT.ui.TooltipSlideOut=$iTXT.core.Class.create($iTXT.ui.ComponentBase,{init:function(_options,$super)
{var defOpts=$iTXT.core.Util.extend({bgcol:"#545454",op:0.85,t:"Advertisement",h:272,w:310,d:"r",pding:10,content:"",duration:0.5},_options);$super(defOpts);this.options.content=this.options.content.replace("${REFERER}",escape(document.location.href));this.isOpen=false;this.isOpening=false;var b=$iTXT.core.Builder;var isVertical=(("t"==this.options.d)||("b"==this.options.d));this.pding=this.options.pding;this.wP=isVertical?10:(10+this.pding);this.hP=isVertical?(22+this.pding):22;this.aw=this.options.w;this.ah=this.options.h;this.cw=this.aw-this.wP;this.ch=this.ah-this.hP;var bgc=b.make("DIV",{className:"vm_ttso_bg",style:"opacity:"+this.options.op+";filter: alpha(opacity = "+this.options.op+");width: "+this.aw+"px; height: "+this.ah+"px;"},[this._header(b),this._middle(b),this._footer(b)]);var cl=("r"==this.options.d)?(5+this.pding):5;var ct=("b"==this.options.d)?(5+this.pding):17;var cc=b.make("DIV",{id:"vm_ttso_cnt",style:"left: "+cl+"px; top: "+ct+"px; width: "+this.cw+"px; height: "+this.ch+"px;"});var ttll=("r"==this.options.d)?(5+this.pding):5;var ttlt=("b"==this.options.d)?(this.ah-17):0;var ttl=b.make("DIV",{className:"vm_ttso_ttl",style:"left: "+ttll+"px; top: "+ttlt+"px;"},[this.options.t]);var srcPath="http://images.intellitxt.com/ast/vmimages/ttso/";var onSrc=srcPath+"close_on.gif";var offSrc=srcPath+"close_off.gif";var cbtnr=("l"==this.options.d)?(5+this.pding):5;var cbtnt=("b"==this.options.d)?(this.ah-17):0;var cBtn=b.make("DIV",{className:"vm_ttso_cbtn",style:"right: "+cbtnr+"px; top: "+cbtnt+"px;"},[b.make("IMG",{src:offSrc,onClick:"$iTXT.core.$(document).itxtFire('$iTXT:ttso:close');",onMouseOver:"this.src='"+onSrc+"'",onMouseOut:"this.src='"+offSrc+"'"})]);var shad=b.make("DIV",{id:"ttso_shad",style:"width: 0px; height: "+this.ah+"px;"},[b.make("IMG",{className:"vm_std vm_shad2",src:RimC('sh_305x200.png',''),style:"width: "+this.aw+"px; height: "+this.ah+"px;"})]);this.rootElement=b.make("DIV",{id:"vm_ttso"},[shad,bgc,cc,ttl,cBtn]);var t=this;$iTXT.core.$(document).itxtSubscribe("$iTXT:ttso:close",function(e)
{if(t.isOpen)
{t.close();}});$iTXT.core.$(document).itxtSubscribe("$iTXT:tt:open",function(e)
{if(!t.isOpen)
{t.open();}});},open:function()
{if(this.options.beforeOpen)
{this.options.beforeOpen();}
var dx=0;var dy=0;switch(this.options.d)
{case'r':dx=(this.aw-this.pding);break;case'l':dx=-(this.aw-this.pding);break;case't':dy=-(this.ah-this.pding);break;case'b':dy=(this.ah-this.pding);break;}
var t=this;var afFunc=function()
{t.isOpen=true;t.isOpening=false;$iTXT.core.$("vm_ttso_cnt").innerHTML=t.options.content;}
t.isOpening=true;this._slide(dx,dy,afFunc);},close:function()
{var dx=0;var dy=0;switch(this.options.d)
{case'r':dx=-(this.aw-this.pding);break;case'l':dx=(this.aw-this.pding);break;case't':dy=(this.ah-this.pding);break;case'b':dy=-(this.ah-this.pding);break;}
$iTXT.core.$("vm_ttso_cnt").innerHTML="";var t=this;var afFunc=function()
{t.isOpen=false;}
this._slide(dx,dy,afFunc);},setBounds:function(w,h)
{},_slide:function(dx,dy,afterFinish)
{var t=this;new $iTXT.fx.Move({target:"vm_ttso",start:true,dX:dx,dY:dy,duration:this.options.duration,afterFinish:afterFinish,afterUpdate:function(v)
{var l=$iTXT.core.$("vm_ttso").offsetLeft;if(l>0)
{l+=10;}
$iTXT.core.$("ttso_shad").itxtSetStyle({width:l+"px"});}});},_header:function(b)
{var crnCont=b.make("DIV",{className:"vm_ttso_hdr"});for(var i=0;i<6;i++)
{crnCont.appendChild(b.make("B",{className:"vm_ttso_tc"+i,style:"background-color: "+this.options.bgcol+";"}));}
return crnCont;},_middle:function(b)
{var mdlDiv=b.make("DIV",{className:"vm_ttso_mdl",style:"background-color: "+this.options.bgcol+"; height: "+(this.ah-12)+"px;"});return mdlDiv;},_footer:function(b)
{var crnCont=b.make("DIV",{className:"vm_ttso_ftr",style:"top: "+(this.ah-6)+"px;"});for(var i=0;i<6;i++)
{crnCont.appendChild(b.make("B",{className:"vm_ttso_bc"+i,style:"background-color: "+this.options.bgcol+";"}));}
return crnCont;},_content:function(b)
{return"";}});}
$iTXT.js.loader["$iTXT.ui.WindowBase"]=true;$iTXT.ui.WindowBase_Load=function(){var undefined;$iTXT.ui.WindowBase=$iTXT.core.Class.create($iTXT.ui.ComponentBase,{init:function(_options,$super)
{var defOpts=$iTXT.core.Util.extend({},_options);$super(defOpts);}});}