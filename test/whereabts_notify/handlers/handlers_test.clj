(ns whereabts-notify.handlers.handlers-test
	(:use 
		midje.sweet
		whereabts-notify.handlers.handlers
		whereabts-notify.handlers.likes-handler
		whereabts-notify.handlers.replies-handler))

(fact "should define message handlers for each channel"
	message-handlers => {
		"message.replies" reply-handler
		"message.likes" likes-handler})


