(ns whereabts-notify.handlers
	(:use [whereabts-notify.replies])
	(:require [taoensso.carmine :as carmine]))

(def reply-channel "message.replies")

(defn- notify-from-msg [msg]
	(get msg 2))

(defn reply-handler [msg]
	(when (not (nil? (notify-from-msg msg))) 
		(notify-user-on-reply (notify-from-msg msg)))
	msg)

(def message-handlers
	{reply-channel reply-handler})