/* This source code is Copyright (c) Vibrant Media 2001-2009 and forms part of the patented Vibrant Media product "IntelliTXT" (sm). */ 
$iTXT.js.loader["$iTXT.data.AdLogger"]=true;$iTXT.data.AdLogger_Load=function(){var undefined;$iTXT.data.AdLogger=$iTXT.core.Class.create({server:"",qavTID:-1,init:function(_opts)
{this.server=_opts.server||"mymachine";$iTXT.core.$(document).itxtSubscribe("$iTXT:data:adlog",$iTXT.core.Event.bind(this,this.log));$iTXT.core.$(document).itxtSubscribe("$iTXT:tt:open:prechromerw",$iTXT.core.Event.bind(this,this.ttOpen));$iTXT.core.$(document).itxtSubscribe("$iTXT:tt:close",$iTXT.core.Event.bind(this,this.ttClose));},log:function(e)
{this._log(e.data);},_log:function(opts)
{var url='http://'+this.server+'/al.asp?ts='+(new Date()).getTime();for(pn in opts)
{url+="&"+pn+"="+encodeURIComponent(opts[pn]);}
dbM("Logging Advert Event: "+url);$iTXT.core.Util.dropScript(url,function(){document.body.removeChild(this);});},ttOpen:function()
{this._cancelQAVT();if(null!=$iTXT.tglb&&'undefined'!=typeof $iTXT.tglb&&'undefined'!=typeof $iTXT.tglb.getAVStub)
{return;}
var iTt=giTt();var ad=iTt.vm.a;var qavd=ad.params["qavd"]||0;if(qavd>0)
{this.logAV(ad,7,false);var t=this;this.qavTID=window.setTimeout(function(){t.logAV(ad,1,true);},qavd);}
else
{this.logAV(ad,1,false);}},ttClose:function()
{this._cancelQAVT();},logAV:function(ad,avt,uqav)
{var opts={av:avt,ipid:ipid,di:ad.ldid,syid:ad.syid,adid:ad.adid,pid:ad.pid,cc:gGeo.cc,rcc:gGeo.rcc,so:(0==ad.at?9:iSo),mh:gSID,ll:ad.ll,hbll:ad.hbll,id:ad.id,pvu:gPVU,pvm:gPVM,uf:ad.uf,ur:ad.ur,idh:ad.idh}
if(uqav)
{opts.uav=1;}
this._log(opts);if(1==avt)
{var toks=itxtSubclass(gTokVals);if(ad.params["trkimages"]&&$iTXT.core.Util.isArray(ad.params["trkimages"]))
{for(var i=0;i<ad.params["trkimages"].length;i++)
if(""!=ad.params["trkimages"][i])
{var imgSrc=ad.params["trkimages"][i];imgSrc=imgSrc.replace(/_TIMESTAMP_/g,(new Date()).getTime());imgSrc=repToks(imgSrc,true,toks);$iTXT.func.dropBeacon(imgSrc,'img');}}
if(ad.avs)
{itxtDSS(ad.avs);}}},_cancelQAVT:function()
{if(-1!=this.qavTID)
{window.clearTimeout(this.qavTID);this.qavTID=-1;}}});}
$iTXT.js.loader["$iTXT.data.Param"]=true;$iTXT.data.Param_Load=function(){var undefined;$iTXT.data.Param=$iTXT.core.Class.create({paramHash:null,parent:null,init:function(p)
{this.paramHash={}
this.parent=p||null;},get:function(pname,defVal)
{var retVal=defVal||null;if(this.exists(pname))
{retVal=this.paramHash[pname].v;}
else if(this.parent!=null)
{retVal=this.parent.get(pname,defVal);}
return retVal;},set:function(arg1,arg2)
{if($iTXT.core.Util.isString(arg1))
{this.paramHash[arg1]=arg2;}
else if($iTXT.core.Util.isObject(arg1))
{for(name in arg1)
{this.paramHash[name]=arg1[name];}}},unset:function(pname)
{this.paramHash[pname]=null;},exists:function(pname)
{return(this.paramHash[pname]!=null);},parse:function(s,obj)
{var cloneHash={};for(name in this.paramHash)
{if(obj&&obj[name])
{cloneHash[name]=obj[name];}
else
{cloneHash[name]=this.paramHash[name];}}
for(name in cloneHash)
{s=s.replace("${"+name+"}",cloneHash[name]);}
if(this.parent!=null)
{return this.parent.parse(s);}
return s;},_param:function(pname,val,weight)
{var r={n:pname,v:val,w:(weight||0)}
return r;}});}