(ns whereabts-notify.handlers.replies-handler
	(:use 
		whereabts-notify.replies
		whereabts-notify.handlers.util
		validateur.validation)
	(:require [taoensso.carmine :as carmine]))

(def reply-details-validation
	(validation-set
		(presence-of :message-id)
		(presence-of :user-id)
		(presence-of :reply-id)))

(defn reply-handler [msg]
	(let [reply-details (channel-message msg)]
	(when (valid? reply-details-validation reply-details) 
		(notify-message-owner reply-details)
		(notify-message-repliers reply-details))
	msg))