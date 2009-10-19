/* This source code is Copyright (c) Vibrant Media 2001-2009 and forms part of the patented Vibrant Media product "IntelliTXT" (sm). */ 
$iTXT.js.loader["$iTXT.core.Util"]=true;$iTXT.core.Util_Load=function(){var undefined;$iTXT.core.Util={callbackID:0,args:function(args)
{var rA=[];for(var i=0;i<args.length;i++)
{rA[i]=args[i];}
return rA;},extend:function(dest,src)
{for(var p in src)
{if(undefined!=src[p])
{dest[p]=src[p];}}
return dest;},isElement:function(object)
{return!!(object&&object.nodeType==1);},isFunc:function(object)
{return typeof object==="function";},isArray:function(object)
{return this.getClass(object)==="Array";},isString:function(object)
{return this.getClass(object)==="String";},isNumber:function(object)
{return this.getClass(object)==="Number";},isObject:function(object)
{return this.getClass(object)==="Object";},isUndefined:function(object)
{return typeof object===undefined;},getClass:function(object)
{return Object.prototype.toString.call(object).match(/^\[object\s(.*)\]$/)[1];},dropScript:function(src,loadFunc)
{if('string'!=typeof src||!src.match(/^http/)){return;}
try
{var newS=document.createElement('script');var cbFunc=this.callbackFunction(loadFunc,newS);if(src.indexOf('?')!=-1)
{src+="&jscallback="+cbFunc;}
else
{src+="?jscallback="+cbFunc;}
newS.src=src;newS.type='text/javascript';var b=document.getElementsByTagName('body')[0];b.insertBefore(newS,b.firstChild);}
catch(e)
{alert(e);}},callbackFunction:function(f,src)
{var cbName="callback"+(this.callbackID++);$iTXT.js[cbName]=function()
{if(f)
{if(src)
{f.apply(src);}
else
{f();}}}
return"$iTXT.js."+cbName;},getQueryParams:function(srcUrl)
{if(srcUrl.indexOf('?')==-1)
{return{};}
var params={};var qs=srcUrl.substring(srcUrl.indexOf('?')+1);var pairs=qs.split('&');for(var i=0;i<pairs.length;i++)
{var keyPair=pairs[i].split('=');if(keyPair.length==2)
{params[keyPair[0]]=unescape(keyPair[1]);}}
return params;},getScriptBySrc:function(srcUrl)
{var scripts=document.getElementsByTagName("script");for(var i=0;i<scripts.length;i++)
{var s=scripts[i];if(s.src.indexOf(srcUrl)!=-1)
{return s;}}}}}
$iTXT.js.loader["$iTXT.core.Builder"]=true;$iTXT.core.Builder_Load=function(){var undefined;$iTXT.core.Builder={NODEMAP:{AREA:'map',CAPTION:'table',COL:'table',COLGROUP:'table',LEGEND:'fieldset',OPTGROUP:'select',OPTION:'select',PARAM:'object',TBODY:'table',TD:'table',TFOOT:'table',TH:'table',THEAD:'table',TR:'table'},make:function(tagName,attributes,children)
{var tagName=tagName.toUpperCase();var parentTagName=this.NODEMAP[tagName]||'div';var parentTag=document.createElement(parentTagName);try{parentTag.innerHTML="<"+tagName+"></"+tagName+">";}catch(e){}
var element=parentTag.firstChild||null;if(element&&(element.tagName.toUpperCase()!=tagName))
element=element.getElementsByTagName(tagName)[0];if(!element)element=document.createElement(tagName);if(!element)return;if(attributes)
{var attrs=this._attributes(attributes);if(attrs.length){try{parentTag.innerHTML="<"+tagName+" "+attrs+"></"+tagName+">";}
catch(e){}
element=parentTag.firstChild||null;if(!element){element=document.createElement(tagName);for(attr in attributes)
element[attr]=attributes[attr];}
if(element.tagName.toUpperCase()!=tagName)
element=parentTag.getElementsByTagName(tagName)[0];}}
if(children)
{this._children(element,children);}
return $iTXT.core.$(element);},_isSorN:function(param)
{return $iTXT.core.Util.isString(param)||$iTXT.core.Util.isNumber(param);},_children:function(element,children)
{for(var i=0;i<children.length;i++)
{if(this._isSorN(children[i]))
{element.appendChild(document.createTextNode(children[i]));}
else
{element.appendChild(children[i]);}}},_attributes:function(attributes)
{var attrs=[];for(attribute in attributes)
{var an=(attribute=="className")?"class":attribute;attrs.push(an+'="'+attributes[attribute]+'"');}
return attrs.join(" ");}}}
$iTXT.js.loader["$iTXT.core.Class"]=true;$iTXT.core.Class_Load=function(){var undefined;$iTXT.core.Class={create:function()
{var parent=null;var properties=arguments[0];if($iTXT.core.Util.isFunc(properties))
{parent=properties;properties=arguments[1];}
function _newClass()
{this.init.apply(this,arguments);}
if(null!=parent)
{var parentClass=function(){};parentClass.prototype=parent.prototype
_newClass.prototype=new parentClass;}
for(p in properties)
{this._addProperty(_newClass,p,properties[p],parent);}
return _newClass;},_addProperty:function(_class,_property,_value,_parent)
{if($iTXT.core.Util.isFunc(_value)&&_parent&&undefined!=_parent.prototype[_property])
{var _oldValue=_value;_value=function()
{var _instance=this;var _newArgs=$iTXT.core.Util.args(arguments);var _super=function()
{_parent.prototype[_property].apply(_instance,arguments);}
_newArgs.push(_super)
_oldValue.apply(this,_newArgs);}}
_class.prototype[_property]=_value;}}}
$iTXT.js.loader["$iTXT.core.Dom"]=true;$iTXT.core.Dom_Load=function(){var undefined;$iTXT.core.$=function(elmt,dontExt)
{if($iTXT.core.Util.isString(elmt))
{elmt=document.getElementById(elmt);}
if(!elmt)
return null;if(dontExt||(elmt.itxt&&elmt.itxt.domExtended))
{return elmt;}
else
{elmt=$iTXT.core.Util.extend(elmt,$iTXT.core.Dom);elmt.itxt={};elmt.itxt.domExtended=true;return elmt;}}
$iTXT.core.Dom={iTXTDOMExtended:true,iTXTEvents:{},itxtFire:function(type,data)
{$iTXT.core.Event.fire(this,type,data);return this;},itxtSubscribe:function(type,handler)
{$iTXT.core.Event.subscribe(this,type,handler);return this;},itxtUnSubscribe:function(type,handler)
{$iTXT.core.Event.unsubscribe(this,type,handler);return this;},itxtAddClass:function(addClass,removeClass)
{if(this.className)
{var cNs=this.className.split(' ');var newCNs=[];for(var i=0;i<cNs.length;i++)
{var cn=cNs[i];if(cn!=removeClass&&cn!=addClass)
{newCNs.push(cn);}}
newCNs.push(addClass);this.className=newCNs.join(' ');}
else
{this.className=addClass;}
return this;},itxtSetStyle:function(styles)
{if($iTXT.core.Util.isString(styles))
{return this.style.cssText+=";"+styles;}
for(s in styles)
{this.style[s]=styles[s];}
return this;},itxtSetAttribute:function(atts)
{for(attribute in atts)
{this[attribute]=atts[attribute];}}}}
$iTXT.js.loader["$iTXT.core.Event"]=true;$iTXT.core.Event_Load=function(){var undefined;$iTXT.core.Event={bind:function(source,name)
{return function()
{name.apply(source,arguments);}},nsUID:0,subscribe:function(elmt,type,handler)
{elmt=$iTXT.core.$(elmt);var eventName=type;var eventUID=type;if(type.indexOf('.')!=-1)
{var splt=type.split('.');eventName=splt.pop();}
else
{eventUID="evt"+this.nsUID+++"."+eventName;}
var custom=eventName.charAt(0)=='$';if(custom)
{}
else
{if(elmt.addEventListener)
elmt.addEventListener(eventName,handler,false);else if(elmt.attachEvent)
elmt.attachEvent("on"+eventName,handler);}
this._addEvt(elmt,eventName,eventUID,handler);},_addEvt:function(elmt,eventName,eventUID,handler)
{var handlers=elmt.iTXTEvents[eventName]||{};handlers[eventUID]=handler;elmt.iTXTEvents[eventName]=handlers;},_removeEvt:function(elmt,eventName,eventUID)
{var handlers=elmt.iTXTEvents[eventName]||{};var newHandlers={};for(handler in handlers)
{if(handler!=eventUID)
{newHandlers[handler]=handlers[handler];}}
elmt.iTXTEvents[eventName]=newHandlers;},unsubscribe:function(elmt,eventUID,handler)
{elmt=$iTXT.core.$(elmt);var eventName=eventUID;if(eventUID.indexOf('.')!=-1)
{var splt=eventUID.split('.');eventName=splt.pop();}
var custom=eventName.charAt(0)=='$';this._removeEvt(elmt,eventName,eventUID);},fire:function(elmt,eventName,data)
{elmt=$iTXT.core.$(elmt);var custom=eventName.charAt(0)=='$';var handlers=elmt.iTXTEvents[eventName]||{};var event={data:data||{}}
for(handler in handlers)
{handlers[handler].apply(elmt,[event]);}}}}