(defproject whereabts-notify "0.1.0-SNAPSHOT"
  :description "Notifies Whereabts users on various things"
  :url "http://whereabts.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies 
        [[org.clojure/clojure "1.5.1"]
        [com.novemberain/monger "1.6.0"]
        [com.novemberain/validateur "1.5.0"]
				[com.taoensso/carmine "2.0.0"]
        [clj-http "0.7.0"]
        [org.clojure/data.json "0.2.2"]]
   :profiles {
      :dev {
         :dependencies [[midje "1.5.1"]]
         :plugins [[lein-midje "3.0.0"]]}}
  :main whereabts-notify.core)
