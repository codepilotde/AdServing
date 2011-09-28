/*
 * Mad-Advertisement
 * Copyright (C) 2011 Thorsten Marx <thmarx@gmx.net>
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
if (typeof madApi == "undefined") {
	madApi = {};

	madApi.onload = function(func) {
		var oldonload = window.onload;
		if (typeof window.onload != 'function') {
			window.onload = func;
		} else {
			window.onload = function() {
				oldonload();
				func();
			};
		}
	};

	madApi.delegate = function(func, obj, args) {
		var params = args || arguments;
		var f = function() {
			var target = arguments.callee.target;
			// var func = arguments.callee.func;
			return func.apply(target, params);
		};

		f.target = obj;
		f.func = this;

		return f;
	};

	/**
	 * Liefert die Position eines Elements
	 * 
	 * @param obj
	 * @returns {left, top}
	 */
	madApi.position = function(obj) {
		var curleft = 0;
		var curtop = 0;
		if (obj.offsetParent) {
			do {
				curleft += obj.offsetLeft;
				curtop += obj.offsetTop;
			} while (obj = obj.offsetParent);
		}
		return {
			left : curleft,
			top : curtop
		};
	};

	/**
	 * Liefert Höhe und Breite eines Elements
	 * 
	 * @param obj
	 * @returns {height, width}
	 */
	madApi.size = function(obj) {
		return {
			height : obj.offsetHeight,
			width : obj.offsetWidth
		};
	};

	/*
	 * * Check if the browser is Microsoft Internet Explorer compatible * v
	 * version number * returns true if MSIE and version equals or greater
	 */
	madApi.isIE = function(v) {
		var ua = window.navigator.userAgent;
		var msie = ua.indexOf("MSIE ");
		return msie > 0 ? true : false;

		// return madApi.isBrowser("Microsoft", v);
	};

	/*
	 * * Check if the current browser is compatible * b browser name * v version
	 * number (if 0 don't check version) * returns true if browser equals and
	 * version equals or greater
	 */
	madApi.isBrowser = function(b, v) {

		browserOk = false;
		versionOk = false;

		browserOk = (navigator.appName.indexOf(b) != -1);
		if (v == 0)
			versionOk = true;
		else
			versionOk = (v <= parseInt(navigator.appVersion));
		return browserOk && versionOk;
	};

	/**
	 * Liefert die größe des Browserfensters
	 * 
	 * @returns {height, width}
	 */
	madApi.getWindowSize = function() {
		var myWidth = 0, myHeight = 0;
		if (typeof (window.innerWidth) == 'number') {
			// Non-IE
			myWidth = window.innerWidth;
			myHeight = window.innerHeight;
		} else if (document.documentElement
				&& (document.documentElement.clientWidth || document.documentElement.clientHeight)) {
			// IE 6+ in 'standards compliant mode'
			myWidth = document.documentElement.clientWidth;
			myHeight = document.documentElement.clientHeight;
		} else if (document.body
				&& (document.body.clientWidth || document.body.clientHeight)) {
			// IE 4 compatible
			myWidth = document.body.clientWidth;
			myHeight = document.body.clientHeight;
		}
		return {
			width : myWidth,
			height : myHeight
		};
	};

	/**
	 * liefert die Anzahl der Pixel, die nach rechts (x) oder unten (y)
	 * gescrollt worden ist
	 * 
	 * @param obj
	 * @returns {x, y}
	 */
	madApi.getScrollXY = function(obj) {
		var scrOfX = 0, scrOfY = 0;
		if (typeof (window.pageYOffset) == 'number') {
			// Netscape compliant
			scrOfY = window.pageYOffset;
			scrOfX = window.pageXOffset;
		} else if (document.body
				&& (document.body.scrollLeft || document.body.scrollTop)) {
			// DOM compliant
			scrOfY = document.body.scrollTop;
			scrOfX = document.body.scrollLeft;
		} else if (document.documentElement
				&& (document.documentElement.scrollLeft || document.documentElement.scrollTop)) {
			// IE6 standards compliant mode
			scrOfY = document.documentElement.scrollTop;
			scrOfX = document.documentElement.scrollLeft;
		}
		return {
			x : scrOfX,
			y : scrOfY
		};
	};

	/**
	 * Verknüpft ein Event mit einem Element
	 * 
	 * var h1 = document.getElementById('header'); madApi.addEvent(h1, 'click',
	 * doSomething, false);
	 * 
	 * @param elem
	 *            Das Element für das Event
	 * @param evt
	 *            Das Event (z.B. click)
	 * @param func
	 * @param cap
	 */
	madApi.addEvent = function(elem, evt, func, cap) {
		if (elem.attachEvent) {
			// if this evaluates to true, we are working with IE so we use IE's
			// code.
			elem.attachEvent('on' + evt, func);
		} else {
			// the statement has evaluated to false, so we are not in IE/
			// the capture argument is optional. If it's left out, we set it to
			// false:
			if (!cap)
				cap = false;
			// and use the normal code to add our event.
			elem.addEventListener(evt, func, cap);
		}
	}

	/***************************************************************************
	 * Javascript Flash Detection (14.02.2005) (c) Oliver Nowak
	 * http://www.deydesigns.com
	 * 
	 * Syntax: [variable = ] flash_detection(required, max);
	 * 
	 * 
	 * @param integer
	 *            benötigte Flash Plugin Version
	 * @param integer
	 *            maximal zu prüfende Versionen
	 * @return array vorhandene Version (0 = kein Flash Plugin installiert) und
	 *         benötigte Version (integer)
	 * 
	 * 
	 * Hinweise:
	 * 
	 * Wie wir ja alle wissen gibt es keine 100% zuverlässige Flash Detection,
	 * und genauso verhält es sich mit dieser. Sie wird nicht mit allen Systemen
	 * funktionieren. Wie dem auch sei, sie wurde erfolgreich getestet unter
	 * WindowsXP (SP1) mit MSIE 6, NS 7.1, Opera 7.11, Mozilla 1.4 und Firefox
	 * 1.0
	 */
	madApi.flash = function(required, max) {
		var required_version = required;
		var max_version = max;
		var available_version = 0;

		/* dieser Abschnitt ist für NS, Mozilla, Firefox und ähnliche Browser */
		if (typeof (navigator.plugins["Shockwave Flash"]) == "object") {
			/*
			 * Wenn wir hier landen, dann ist Flash installiert, und wir können
			 * die Version aus der Beschreibung auslesen.
			 */
			var description = navigator.plugins["Shockwave Flash"].description;
			available_version = description.substr(16, (description.indexOf(
					".", 16) - 16));
		}
		/*
		 * dieser Abschnitt ist für den IE und ähnliche Browser die ActiveX
		 * benutzen um Flash anzuzeigen.
		 */
		else if (typeof (ActiveXObject) == "function") {
			/*
			 * durchlaufen der Flash Versionen von 2 bis zur maximal zu
			 * prüfenden Version
			 */
			for ( var i = 2; i < (max_version + 1); i++) {
				/*
				 * wir beugen mittels try und catch (JS 1.5+ / IE5+) einem
				 * Fehler vor (welcher einen Abbruch des Scripts zur Folge
				 * hätte), so können wir auch auf ein extra VBScript verzichten.
				 */
				try {
					/*
					 * läßt sich das ActiveX Flash Objekt Version i erstellen,
					 * so ist diese Version auch installiert
					 */
					if (typeof (new ActiveXObject(
							"ShockwaveFlash.ShockwaveFlash." + i)) == "object") {
						available_version = i;
					}
				} catch (error) {
				}
			}
		}

		/* die Werte werden in Form eines Arrays zurückgegeben */
		return {
			available : available_version,
			required : required_version
		};

	};

	/**
	 * Die FX-Effekt Implementierung stammt aus einer externen Bibliothek
	 * http://fx.inetcat.com/
	 * 
	 * @param initElm
	 * @returns
	 */
	madApi.fx = function(initElm) {
		var elm = null;
		if (initElm.nodeType && initElm.nodeType == 1) {
			elm = initElm;
		} else if (String(initElm).match(/^#([^$]+)$/i)) {
			elm = document.getElementById(RegExp.$1 + '');
			if (!elm) {
				return null;
			}
		} else {
			return null;
		}

		if (typeof (elm._fx) != 'undefined' && elm._fx) {
			elm._fx._addSet();
			return elm;
		}

		elm.fxVersion = 0.1;
		elm._fx = {};
		elm._fx.sets = [];
		elm._fx._currSet = 0;

		if (typeof (elm._fxTerminated) != 'undefined') {
			try {
				delete elm._fxTerminated;
			} catch (err) {
				elm._fxTerminated = null;
			}
		}

		var units = {
			'left|top|right|bottom|width|height|margin|padding|spacing|backgroundx|backgroundy' : 'px',
			'font' : 'pt',
			'opacity' : ''
		};

		var isIE = !!navigator.userAgent.match(/MSIE/ig);

		var required = {
			delay : 100,
			step : 5,
			unit : ''
		};

		var handlers = {
			opacity : function(val, unit) {
				val = parseInt(val);
				if (isNaN(val)) {
					if (isIE) {
						var matches = (new RegExp(
								'alpha\\s*\\(opacity\\s*=\\s*(\\d+)\\)'))
								.exec(elm.style.filter + '');
						if (matches)
							return parseInt(matches[1]);
						else
							return 1;
					} else {
						return Math
								.round((elm.style.opacity ? parseFloat(elm.style.opacity)
										: 1) * 100);
					}
				} else {
					val = Math.min(100, Math.max(0, val));
					if (isIE) {
						elm.style.zoom = 1;
						elm.style.filter = 'alpha(opacity=' + val + ');';
					} else {
						elm.style.opacity = val / 100;
					}
				}
			},
			'backgroundx' : function(val, unit) {
				val = parseInt(val);
				var x = 0, y = 0;
				var matches = (new RegExp('^(-?\\d+)[^\\d\\-]+(-?\\d+)'))
						.exec(elm.style.backgroundPosition + '');
				if (matches) {
					x = parseInt(matches[1]);
					y = parseInt(matches[2]);
				}
				if (isNaN(val))
					return x;
				else {
					elm.style.backgroundPosition = val + unit + ' ' + y + unit;
				}
			},
			'backgroundy' : function(val, unit) {
				val = parseInt(val);
				var x = 0, y = 0;
				var matches = (new RegExp('^(-?\\d+)[^\\d\\-]+(-?\\d+)'))
						.exec(elm.style.backgroundPosition + '');
				if (matches) {
					x = parseInt(matches[1]);
					y = parseInt(matches[2]);
				}
				if (isNaN(val))
					return y;
				else {
					elm.style.backgroundPosition = x + unit + ' ' + val + unit;
				}
			}
		};

		var defaults = {
			width : function() {
				return parseInt(elm.offsetWidth)
			},
			height : function() {
				return parseInt(elm.offsetHeight)
			},
			left : function() {
				var left = 0;
				for ( var el = elm; el; el = el.offsetParent)
					left += parseInt(el.offsetLeft);
				return left;
			},
			top : function() {
				var top = 0;
				for ( var el = elm; el; el = el.offsetParent)
					top += parseInt(el.offsetTop);
				return top;
			}
		};

		elm.fxAddSet = function() {
			this._fx._addSet();
			return this;
		};

		elm.fxHold = function(time, set) {
			if (elm._fx.sets[this._fx._currSet]._isrunning)
				return this;

			var set = parseInt(set);
			this._fx.sets[isNaN(set) ? this._fx._currSet : set]._holdTime = time;
			return this;
		};

		elm.fxAdd = function(params) {
			var currSet = this._fx._currSet;

			if (this._fx.sets[currSet]._isrunning)
				return this;

			for ( var p in required) {
				if (!params[p])
					params[p] = required[p]
			}
			;
			if (!params.unit) {
				for ( var mask in units)
					if ((new RegExp(mask, 'i').test(params.type))) {
						params.unit = units[mask];
						break;
					}
			}
			;

			params.onstart = (params.onstart && params.onstart.call) ? params.onstart
					: function() {
					};
			params.onfinish = (params.onfinish && params.onfinish.call) ? params.onfinish
					: function() {
					};

			if (!this._fx[params.type]) {
				if (handlers[params.type])
					this._fx[params.type] = handlers[params.type];
				else {
					var elm = this;
					this._fx[params.type] = function(val, unit) {
						if (typeof (val) == 'undefined')
							return parseInt(elm.style[params.type]);
						else
							elm.style[params.type] = parseInt(val) + unit;
					}
				}
			}
			;
			if (isNaN(params.from)) {
				if (isNaN(this._fx[params.type]()))
					if (defaults[params.type])
						params.from = defaults[params.type]();
					else
						params.from = 0;
				else
					params.from = this._fx[params.type]();
			}
			params._initial = params.from;
			this._fx[params.type](params.from, params.unit);
			this._fx.sets[currSet]._queue.push(params);
			return this;
		};

		elm.fxRun = function(finalCallback, loops, loopCallback) {
			var currSet = elm._fx._currSet;

			if (this._fx.sets[currSet]._isrunning) {
				return this;
			}

			setTimeout(
					function() {
						if (elm._fx.sets[currSet]._isrunning)
							return elm;
						elm._fx.sets[currSet]._isrunning = true;

						if (elm._fx.sets[currSet]._effectsDone > 0)
							return elm;
						elm._fx.sets[currSet]._onfinal = (finalCallback && finalCallback.call) ? finalCallback
								: function() {
								};
						elm._fx.sets[currSet]._onloop = (loopCallback && loopCallback.call) ? loopCallback
								: function() {
								};
						if (!isNaN(loops))
							elm._fx.sets[currSet]._loops = loops;

						for ( var i = 0; i < elm._fx.sets[currSet]._queue.length; i++) {
							elm._fx.sets[currSet]._queue[i].onstart.call(elm);
							elm._fx._process(currSet, i);
						}
					}, elm._fx.sets[currSet]._holdTime);

			return this;
		};

		elm.fxPause = function(status, setNum) {
			this._fx.sets[!isNaN(setNum) ? setNum : this._fx._currSet]._paused = status;
			return this;
		};

		elm.fxStop = function(setNum) {
			this._fx.sets[!isNaN(setNum) ? setNum : this._fx._currSet]._stoped = true;
			return this;
		};

		elm.fxReset = function() {
			for ( var i = 0; i < this._fx.sets.length; i++) {
				for ( var j = 0; j < this._fx.sets[i]._queue.length; j++) {
					var params = this._fx.sets[i]._queue[j];
					if (isNaN(params._initial))
						this._fx[params.type]('', '');
					else
						this._fx[params.type](params._initial, params.unit);
				}
			}
			var del = [ '_fx', 'fxHold', 'fxAdd', 'fxAddSet', 'fxRun',
					'fxPause', 'fxStop', 'fxReset' ];
			for ( var i = 0; i < del.length; i++)
				try {
					delete this[del[i]]
				} catch (err) {
					this[del[i]] = null
				}
			this._fxTerminated = true;
		};

		elm._fx._addSet = function() {
			var currSet = this.sets.length;
			this._currSet = currSet;
			this.sets[currSet] = {};
			this.sets[currSet]._loops = 1;
			this.sets[currSet]._stoped = false;
			this.sets[currSet]._queue = [];
			this.sets[currSet]._effectsDone = 0;
			this.sets[currSet]._loopsDone = 0;
			this.sets[currSet]._holdTime = 0;
			this.sets[currSet]._paused = false;
			this.sets[currSet]._isrunning = false;
			this.sets[currSet]._onfinal = function() {
			};

			return this;
		};

		elm._fx._process = function(setNum, effectNum) {
			if (!this.sets[setNum] || this.sets[setNum]._stoped
					|| elm._fxTerminated)
				return;
			var ef = this.sets[setNum]._queue[effectNum];
			var param = this[ef.type]();

			if ((ef.step > 0 && param + ef.step <= ef.to)
					|| (ef.step < 0 && param + ef.step >= ef.to)) {
				if (!this.sets[setNum]._paused)
					this[ef.type](param + ef.step, ef.unit);
				var inst = this;
				setTimeout(function() {
					if (inst._process)
						inst._process(setNum, effectNum)
				}, ef.delay);
			} else {
				this[ef.type](ef.to, ef.unit);
				this.sets[setNum]._effectsDone++;
				ef.onfinish.call(elm);
				if (this.sets[setNum]._queue.length == this.sets[setNum]._effectsDone) {
					this.sets[setNum]._effectsDone = 0;
					this.sets[setNum]._loopsDone++;
					this.sets[setNum]._onloop.call(elm,
							this.sets[setNum]._loopsDone);
					if (this.sets[setNum]._loopsDone < this.sets[setNum]._loops
							|| this.sets[setNum]._loops == -1) {
						for ( var i = 0; i < this.sets[setNum]._queue.length; i++) {
							this[ef.type](ef.from,
									this.sets[setNum]._queue[i].unit);
							this.sets[setNum]._queue[i].onstart.call(elm,
									this.sets[setNum]._loopsDone);
							this._process(setNum, i);
						}
					} else {
						this.sets[setNum]._onfinal.call(elm);
					}
				}
			}
		};

		elm._fx._addSet();
		return elm;
	};

	madApi.ajax = {
		// Create a xmlHttpRequest object - this is the constructor.
		getHTTPObject : function() {
			var http = false;
			// Use IE's ActiveX items to load the file.
			if (typeof ActiveXObject != 'undefined') {
				try {
					http = new ActiveXObject("Msxml2.XMLHTTP");
				} catch (e) {
					try {
						http = new ActiveXObject("Microsoft.XMLHTTP");
					} catch (E) {
						http = false;
					}
				}
				// If ActiveX is not available, use the XMLHttpRequest of
				// Firefox/Mozilla etc. to load the document.
			} else if (window.XMLHttpRequest) {
				try {
					http = new XMLHttpRequest();
				} catch (e) {
					http = false;
				}
			}
			return http;
		},

		// This function is called from the user's script.
		// Arguments -
		// url - The url of the serverside script that is to be called. Append
		// all
		// the arguments to
		// this url - eg. 'get_data.php?id=5&car=benz'
		// callback - Function that must be called once the data is ready.
		// format - The return type for this function. Could be 'xml','json' or
		// 'text'. If it is json,
		// the string will be 'eval'ed before returning it. Default:'text'
		// method - GET or POST. Default 'GET'
		load : function(url, callback, format, method, opt) {
			var http = this.init(); // The XMLHttpRequest object is recreated at
			// every call - to defeat Cache problem in IE
			if (!http || !url)
				return;
			// XML Format need this for some Mozilla Browsers
			if (http.overrideMimeType)
				http.overrideMimeType('text/xml');

			if (!method)
				method = "GET";// Default method is GET
			if (!format)
				format = "text";// Default return type is 'text'
			if (!opt)
				opt = {};
			format = format.toLowerCase();
			method = method.toUpperCase();

			// Kill the Cache problem in IE.
			var now = "uid=" + new Date().getTime();
			url += (url.indexOf("?") + 1) ? "&" : "?";
			url += now;

			var parameters = null;

			if (method == "POST") {
				var parts = url.split("\?");
				url = parts[0];
				parameters = parts[1];
			}
			http.open(method, url, true);

			if (method == "POST") {
				http.setRequestHeader("Content-type",
						"application/x-www-form-urlencoded");
				http.setRequestHeader("Content-length", parameters.length);
				http.setRequestHeader("Connection", "close");
			}

			var ths = this;// Closure
			if (opt.handler) { // If a custom handler is defined, use it
				http.onreadystatechange = function() {
					opt.handler(http);
				};
			} else {
				http.onreadystatechange = function() {// Call a function when
					// the
					// state changes.
					if (http.readyState == 4) {// Ready State will be 4 when
						// the
						// document is loaded.
						if (http.status == 200) {
							var result = "";
							if (http.responseText)
								result = http.responseText;
							// If the return is in JSON format, eval the result
							// before returning it.
							if (format.charAt(0) == "j") {
								// \n's in JSON string, when evaluated will
								// create
								// errors in IE
								result = result.replace(/[\n\r]/g, "");
								result = eval('(' + result + ')');

							} else if (format.charAt(0) == "x") { // XML
								// Return
								result = http.responseXML;
							}

							// Give the data to the callback function.
							if (callback)
								callback(result);
						} else {
							if (opt.loadingIndicator)
								document.getElementsByTagName("body")[0]
										.removeChild(opt.loadingIndicator); // Remove
							// the
							// loading
							// indicator
							if (opt.loading)
								document.getElementById(opt.loading).style.display = "none"; // Hide
							// the
							// given
							// loading
							// indicator.

							if (error)
								error(http.status);
						}
					}
				}
			}
			http.send(parameters);
		},
		bind : function(user_options) {
			var opt = {
				'url' : '', // URL to be loaded
				'onSuccess' : false, // Function that should be called at
				// success
				'onError' : false, // Function that should be called at error
				'format' : "text", // Return type - could be 'xml','json' or
				// 'text'
				'method' : "GET", // GET or POST
				'update' : "", // The id of the element where the resulting
				// data
				// should be shown.
				'loading' : "", // The id of the loading indicator. This will be
				// set
				// to display:block when the url is loading and to
				// display:none when the data has finished loading.
				'loadingIndicator' : "" // HTML that would be inserted into the
			// document once the url starts loading and
			// removed when the data has finished
			// loading. This will be inserted into a div
			// with class name 'loading-indicator' and
			// will be placed at 'top:0px;left:0px;'
			}
			for ( var key in opt) {
				if (user_options[key]) {// If the user given options contain any
					// valid option, ...
					opt[key] = user_options[key];// ..that option will be put
					// in
					// the opt array.
				}
			}

			if (!opt.url)
				return; // Return if a url is not provided

			var div = false;
			if (opt.loadingIndicator) { // Show a loading indicator from the
				// given
				// HTML
				div = document.createElement("div");
				div
						.setAttribute("style",
								"position:absolute;top:0px;left:0px;");
				div.setAttribute("class", "loading-indicator");
				div.innerHTML = opt.loadingIndicator;
				document.getElementsByTagName("body")[0].appendChild(div);
				this.opt.loadingIndicator = div;
			}
			if (opt.loading)
				document.getElementById(opt.loading).style.display = "block"; // Show
			// the
			// given
			// loading
			// indicator.

			this
					.load(
							opt.url,
							function(data) {
								if (opt.onSuccess)
									opt.onSuccess(data);
								if (opt.update)
									document.getElementById(opt.update).innerHTML = data;

								if (div)
									document.getElementsByTagName("body")[0]
											.removeChild(div); // Remove
								// the
								// loading
								// indicator
								if (opt.loading)
									document.getElementById(opt.loading).style.display = "none"; // Hide
								// the
								// given
								// loading
								// indicator.
							}, opt.format, opt.method, opt);
		},
		init : function() {
			return this.getHTTPObject();
		}
	};
}

function mad_expandableImage_open(idsmall, idbig) {
	if (document.getElementById(idbig).style.opacity != 0) {
		return;
	}

	var pos = madApi.position(document.getElementById(idsmall));
	var size = madApi.size(document.getElementById(idsmall));
	var winSize = madApi.getWindowSize();

	var position = {};
	position.top = pos.top;
	position.left = (pos.left + size.width);

	var rightSpace = winSize.width - (pos.left + size.width);
	/*
	 * der rechte Platz ist kleiner als das expandierte Banner, dann wir es auf
	 * der Linken Seite angezeigt
	 */
	if (rightSpace < 300) {
		position.top = pos.top;
		position.left = (pos.left - 300);
	}

	document.getElementById(idbig).style.left = position.left + "px";
	document.getElementById(idbig).style.top = position.top + "px";

	if (madApi.isIE(0)) {
		document.getElementById(idbig).style.opacity = 1;
	} else {
		var appear = {
			type : 'opacity',
			from : 0,
			to : 100,
			step : 10,
			delay : 10
		};
		madApi.fx(document.getElementById(idbig)).fxAdd(appear).fxRun(null, 1);
	}

}
function mad_expandableImage_close(idbig) {
	if (document.getElementById(idbig).style.opacity == 0) {
		return;
	}

	if (madApi.isIE(0)) {
		document.getElementById(idbig).style.opacity = 0;
	} else {
		var disappear = {
			type : 'opacity',
			from : 100,
			to : 0,
			step : 10,
			delay : 10
		};
		madApi.fx(document.getElementById(idbig)).fxAdd(disappear).fxRun(null,
				1);
	}
}