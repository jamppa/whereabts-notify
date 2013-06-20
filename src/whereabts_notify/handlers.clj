(ns whereabts-notify.handlers
	(:use 
		[whereabts-notify.replies]
		[validateur.validation])
	(:require [taoensso.carmine :as carmine]))

(def reply-channel "message.replies")
(def reply-details-validation
	(validation-set
		(presence-of :replied-message)
		(presence-of :user-to-notify)
		(presence-of :reply)))

(defn- details-from-msg [msg]
	(get msg 2))

(defn- valid-details? [details]
	(valid? reply-details-validation details))

(defn reply-handler [msg]
	(when (valid-details? (details-from-msg msg)) 
		(notify-user-on-reply (details-from-msg msg)))
	msg)

(def message-handlers
	{reply-channel reply-handler})