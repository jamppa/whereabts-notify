(ns whereabts-notify.core
	(:require [taoensso.carmine :as carmine])
	(:use [whereabts-notify.handlers])
  (:gen-class))

(def redis-pool (carmine/make-conn-pool))
(def redis-spec (carmine/make-conn-spec))

(defn -main
  [& args]
  (carmine/with-new-pubsub-listener 
  	redis-spec message-handlers (carmine/subscribe reply-channel)))
