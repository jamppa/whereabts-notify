(ns whereabts-notify.handlers.likes-handler
	(:use 
		whereabts-notify.likes
		whereabts-notify.handlers.util
		validateur.validation)
	(:require [taoensso.carmine :as carmine]))

(def like-details-validation
	(validation-set
		(presence-of :user-id)
		(presence-of :message-owner-id)
		(presence-of :message-id)))

(defn likes-handler [msg]
	(let [like-details (channel-message msg)]
		(when (valid? like-details-validation like-details)
			(notify-message-owner-on-like like-details))
		msg))