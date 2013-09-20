(ns whereabts-notify.handlers.handlers
	(:use 
		whereabts-notify.handlers.likes-handler
		whereabts-notify.handlers.replies-handler
		whereabts-notify.handlers.follow-handler)
	(:require [taoensso.carmine :as carmine]))

(def reply-channel "message.replies")
(def likes-channel "message.likes")
(def follow-channel "users.follow")

(def message-handlers
	{
		reply-channel reply-handler
		likes-channel likes-handler
		follow-channel follow-handler})