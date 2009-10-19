/* This source code is Copyright (c) Vibrant Media 2001-2009 and forms part of the patented Vibrant Media product "IntelliTXT" (sm). */ 
$iTXT.js.loader["$iTXT.fx.Base"]=true;$iTXT.fx.Base_Load=function(){var undefined;$iTXT.fx.Transition={Linear:function(v)
{return v;},EaseIn:function(v)
{return Math.pow(v,1.5*2);},EaseOut:function(v)
{return 1-Math.pow(1-v,1.5*2);}}
$iTXT.fx.Base=$iTXT.core.Class.create({queue:null,init:function(_options)
{this.options={delay:0,from:0,to:1.0,fps:60,duration:2,transition:$iTXT.fx.Transition.Linear,start:false};$iTXT.core.Util.extend(this.options,_options);if(this.options.target)
{this.options.target=$iTXT.core.$(this.options.target);}
this.valueChange=this.options.to-this.options.from;this.totalDuration=this.options.duration*1000;this.intervalDuration=Math.round(1000/this.options.fps);this.totalFrames=Math.round(this.options.fps*this.options.duration);if(this.options.start)
{this.delayedStart();}},_start:function()
{this.start();},delayedStart:function()
{setTimeout($iTXT.core.Event.bind(this,this._start),this.options.delay*1000);},start:function()
{this.running=true;this.notify("beforeStart");this.position=this.options.from;this.currentFrame=0;this.startTime=new Date().getTime();this.interval=setInterval($iTXT.core.Event.bind(this,this.loop),this.intervalDuration);this.notify("afterStart");},notify:function(name,details)
{if((undefined!=this.options[name])&&($iTXT.core.Util.isFunc(this.options[name])))
{this.options[name](details);}},loop:function()
{if(this.running)
{var now=new Date().getTime();if(now>this.startTime+this.totalDuration)
{this.running=false;this.finish();return;}
var pos=(now-this.startTime)/this.totalDuration;var frame=Math.round(this.totalFrames*pos);if(frame>this.currentFrame)
{this.currentFrame=frame;this.render(pos);}}},finish:function()
{this.notify("beforeFinish");clearInterval(this.interval);this.render(1.0);this.notify("afterFinish");if(this.queue!=null)
{this.queue.pop();}},render:function(pos)
{this.notify("beforeUpdate",this.position);this.position=this.options.from+(this.valueChange*this.options.transition(pos));this.update();this.notify("afterUpdate",this.position);},update:function()
{}});}
$iTXT.js.loader["$iTXT.fx.Fade"]=true;$iTXT.fx.Fade_Load=function(){var undefined;$iTXT.fx.Fade=$iTXT.core.Class.create($iTXT.fx.Base,{init:function(_options,$super)
{this.defaultOptions={direction:'in'};$super($iTXT.core.Util.extend(this.defaultOptions,_options));this.target=this.options.target;},update:function()
{var o=(this.position);if('out'==this.options.direction)
o=1-o;if(window.ActiveXObject)
{this.target.style['filter']="alpha(opacity="+Math.round(100*o)+");zoom: 1;";}
else
{this.target.style.opacity=o;}}});}
$iTXT.js.loader["$iTXT.fx.Move"]=true;$iTXT.fx.Move_Load=function(){var undefined;$iTXT.fx.Move=$iTXT.core.Class.create($iTXT.fx.Base,{init:function(_options,$super)
{this.defaultOptions={dX:null,dY:null,x:null,y:null};$super($iTXT.core.Util.extend(this.defaultOptions,_options));},start:function($super)
{this.target=this.options.target;this.startX=this.target.offsetLeft;this.startY=this.target.offsetTop;if(this.options.dX!=null&&this.options.dY!=null)
{this.finishX=this.startX+this.options.dX;this.finishY=this.startY+this.options.dY;}
else
{this.finishX=this.options.x;this.finishY=this.options.y;}
this.changeX=this.finishX-this.startX;this.changeY=this.finishY-this.startY;$super();},update:function()
{var newL=Math.round(this.startX+(this.changeX*this.position));var newT=Math.round(this.startY+(this.changeY*this.position));this.target.itxtSetStyle({left:newL+"px",top:newT+"px",right:"",bottom:""});}});}
$iTXT.js.loader["$iTXT.fx.Queue"]=true;$iTXT.fx.Queue_Load=function(){var undefined;$iTXT.fx.Queue=$iTXT.core.Class.create({queue:null,queueIndex:null,init:function(effect)
{this.queueIndex=0;this.queue=[];this.push(effect);effect.delayedStart();},push:function(effect)
{this.queue.push(effect);effect.queue=this;return this;},pop:function()
{this.queueIndex++;if(this.queueIndex<this.queue.length)
{this.queue[this.queueIndex].delayedStart();}
return this;}});}