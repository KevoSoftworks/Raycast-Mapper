style.json options

NOTE:
	Every element is a block-type element
	*only valid for absolute positioning

Valid units of measurements:
	rw: render width
	rh: render hight
	ww: window width
	wh: window height
	px: pixels
	%w: width relative to parent element
	%h: height relative to parent element
	auto: let the renderer decide

Valid keys:
	width: [unit]
	height: [unit]
	position: [absolute/static]
	*location: [top/center/bottom] [left/center/right]
	*top: [unit]
	*left: [unit]
	*right: [unit]
	*bottom: [unit]
	margin-top: [unit]
	margin-left: [unit]
	margin-right: [unit]
	margin-bottom: [unit]
	border: [thickness] [color-hex]
	background-color: [color-hex]
	color: [color-hex]
	
Valid modifiers:
	onHover
	onActive
	onClick