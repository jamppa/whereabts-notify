(ns whereabts-notify.handlers
	(:use [whereabts-notify.replies])
	(:require [taoensso.carmine :as carmine]))

(def reply-channel "replies")

(defn reply-handler [msg]
	(when (not (nil? (get msg 2))) 
		(notify-user-on-reply (get msg 2)))
	msg)

(def message-handlers
	{reply-channel reply-handler})