(ns whereabts-notify.handlers.follow-handler
	(:use
		whereabts-notify.handlers.util
		whereabts-notify.follow
		validateur.validation))

(def follow-details-validation
	(validation-set
		(presence-of :follower-id)
		(presence-of :following-id)))

(defn follow-handler [msg]
	(let [follow-details (channel-message msg)]
		(when (valid? follow-details-validation follow-details)
			(notify-user-on-new-follower follow-details))
		msg))