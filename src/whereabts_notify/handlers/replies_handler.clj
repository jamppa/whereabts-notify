(ns whereabts-notify.handlers.replies-handler
	(:use 
		whereabts-notify.replies
		validateur.validation)
	(:require [taoensso.carmine :as carmine]))

(defn- details-from-msg [msg]
	(get msg 2))

(def reply-details-validation
	(validation-set
		(presence-of :message-id)
		(presence-of :user-id)
		(presence-of :reply-id)))

(defn reply-handler [msg]
	(let [reply-details (details-from-msg msg)]
	(when (valid? reply-details-validation reply-details) 
		(notify-message-owner reply-details)
		(notify-message-repliers reply-details))
	msg))