(ns ui.dashboard.core
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require
   [reagent.core :as reagent]
   [ui.pages :as pages]
   [ui.routes :refer [href]]
   [re-frame.core :as rf]
   [ui.widgets :as wgt]
   [ui.styles :as styles]
   [clojure.string :as str]))

(defn index [params]
  [:div.container
   [:h1 "Dashboard"]])

(defn avatar []
  [:img.avatar
   {:src (str "https://randomuser.me/api/portraits/men/" (rand-int 100) ".jpg")}])

(defn user [name]
  [:a.assignee
   {:href (href :users "niquiola")}
   (avatar)
   name])

(defn tasks [params]
  [:div
   (styles/style
    [:div
     [:.activities {:border-left "4px solid #009688"
                    :padding-left "20px"}]

     [:.history {:border-left "4px solid #888"
                    :padding-left "20px"}]
     [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
           :font-size "20px"
           :line-height "40px"}]
     [:.item {:padding "5px 0"
              :color "#555"
              :display "block"}
      [:.fa {:color "gray"}]
      [:.title {:margin "0px 10px"}]
      [:a.assignee {:margin-right "10px"
                    :font-size "14px"
                    :color "#555"}
       [:.fa {:margin "0 5px"}]
       [:img.avatar {:width "18px"
                     :height "18px"
                     :margin "0 5px"
                     :border "1px solid #ddd"
                     :display "inline-block"
                     :border-radius "50%"}]
       ]
      ]])
   [:div.activities
    [:h3 "Activities"]
    [:a.item 
     [wgt/icon :paper-plane]
     [:a.title {:href (href :tasks :claim-submit)} "Submit claims for 08/23/17"]
     (user "Nikolai Lee")]
    [:a.item 
     [wgt/icon :eye]
     [:a.title {:href (href :tasks :check-claim)}
      "Check claim for Drago Ivankovichi 08/31/17"]
     (user "John Ivanov")]
    [:a.item 
     [wgt/icon :bar-chart]
     [:a.title {:href (href :tasks :claim-report)}
      "Billing report for July"]
     (user "Will Smith")]
    [:a.item 
     [wgt/icon :exclamation-triangle]
     [:a.title {:href (href :tasks :claim-era)}
      "Check ERA for John Johanson for 08/01/17"]
     (user "Andrew Dunston")]
    ]
   
   [:br]
   [:div.history
   [:h3 "History"]

   [:a "Today"]

   [:a.item 
    [:a.assignee "12:00"]
    [wgt/icon :exclamation-triangle]
    [:a.title {:href (href :tasks :check-claim)}
     "Check ERA for John Johanson for 08/01/17"]
    [:a.assignee {:href (href :users "niquiola")} [wgt/icon :users] "Billing team"]]
   [:a.item 
    [:a.assignee "11:15"]
    [wgt/icon :bar-chart]
    [:a.title {:href (href :tasks :claim-report)}
     "Billing report for June"]
    [:a.assignee {:href (href :users "niquiola")} [wgt/icon :users] "Billing team"]]
   [:a.item 
    [:a.assignee "10:15"]
    [wgt/icon :bar-chart]
    [:a.title {:href (href :tasks :claim-report)}
     "Billing report for July"]
    [:a.assignee {:href (href :users "niquiola")} [wgt/icon :users] "Billing team"]]
   [:a.item 
    [:a.assignee "12:00"]
    [wgt/icon :exclamation-triangle]
    [:a.title {:href (href :tasks :claim-report)}
     "Check ERA for John Johanson for 08/01/17"]
    [:a.assignee {:href (href :users "niquiola")} [wgt/icon :users] "Billing team"]]
   [:a.item 
    [:a.assignee "10:15"]
    [wgt/icon :exclamation-triangle]
    [:a.title {:href (href :tasks :claim-report)}
     "Check ERA for John Johanson for 08/01/17"]
    [:a.assignee {:href (href :users "niquiola")} [wgt/icon :users] "Billing team"]]
    ]
   ])


(defn claim-check [params]
  [:div
   (styles/style
    [:div
     [:.form {:border-left "4px solid #aaa"
              :padding "20px 20px"
              :background-color "#f1f1f1"}]
     [:.task {:padding "15px 20px"
              :border-radius "2px"
              :border-left "4px solid #0275d8"}
      [:.btn-primary {:color "#0275d8"
                      :background-color "white"
                      :border-color "#0275d8"}
       [:&:hover {:background-color "#0275d8"
                  :color "white"}]]
      [:a.assignee {:margin-right "10px"
                    :font-size "14px"
                    :color "#555"}
       [:.fa {:margin "0 5px"}]
       [:img.avatar {:width "18px"
                     :height "18px"
                     :margin "0 5px"
                     :border "1px solid #ddd"
                     :display "inline-block"
                     :border-radius "50%"}]]]])
   [:div.task
    [:h4.title [:b "Task:"] " Check Claim for " ]
    [:br]
    [:b "Start Time: "] "11:00"
    [:br]
    [:b "Status: "] "In Progress"
    [:br]
    [:b "Severity: "] "Normal"
    [:br]
    [:b "Assignee: " (user "Adolf Lungren")]
    [:a {:href "#/me"} "Assign to me"]
    " | "
    [:a {:href "#/me"} " Change"]
    
    ]
   [:br]
   [:form.form
    (styles/style
     [:.form
      {:padding "20px"}
      [:.btn-primary {:color "#0275d8"
                      :background-color "white"
                      :border-color "#0275d8"}
       [:&:hover {:background-color "#0275d8"
                  :color "white"}]]
      [:h3 {:font-size "16px" :margin-bottom "10px" :margin-top "15px" :color "#666" :border-bottom "1px solid #ddd"}]
      [:.form-row {:margin-bottom "10px"}]
      [:label {:color "#888" :width "200px"  :text-align "right" :margin-right "10px"}]
      [:input {:border-top "none"
               :border-left "none"
               :border-right "none"
               :padding "5px 10px"
               :margin-left "10px"
               :border-bottom "1px solid #aaa"}]

      ])
    [:h3 "Patient Info"]
    [:div.form-row
     [:label "Patient Name"]
     [:input {:type "text"
              :placeholder "Family"}]
     [:input {:type "text"
              :placeholder "Given"}]]
    [:div.form-row
     [:label "Patient Address"]
     [:input {:type "text"
              :placeholder "Streat"}]
     [:input {:type "text"
              :placeholder "City"}]
     [:input {:type "text"
              :placeholder "ZIP"}]]
    [:h3 "Insurance Info"]
    [:div.form-row
     [:label "Insurance Type"]
     [:input {:type "text"
              :placeholder "Company"}]
     [:input {:type "text"
              :placeholder "Given"}]]
    [:div.form-row
     [:label "Insurance Type"]
     [:input {:type "text"
              :placeholder "Company"}]
     [:input {:type "text"
              :placeholder "Given"}]]
    [:div.form-row
     [:label "Insurance Type"]
     [:input {:type "text"
              :placeholder "Company"}]
     [:input {:type "text"
              :placeholder "Given"}]]

    [:h3 "Services"]
    [:div.form-row
     [:input {:type "text" :placeholder "Code"}]
     [:input {:type "text" :placeholder "Description"}]
     [:input {:type "text" :placeholder "Diagnosis"}]
     [:input {:type "text" :placeholder "Amount"}]
     " "
     [:button.btn.btn-default  " +"]]
    [:div.form-row
     [:input {:type "text" :placeholder "Code"}]
     [:input {:type "text" :placeholder "Description"}]
     [:input {:type "text" :placeholder "Diagnosis"}]
     [:input {:type "text" :placeholder "Amount"}]
     " "
     [:button.btn.btn-default  " +"]]
    [:div.form-row
     [:input {:type "text" :placeholder "Code"}]
     [:input {:type "text" :placeholder "Description"}]
     [:input {:type "text" :placeholder "Diagnosis"}]
     [:input {:type "text" :placeholder "Amount"}]
     " "
     [:button.btn.btn-default  " +"]]
    [:hr]
    [:button.btn.btn-primary  "Update"]
    

    ]
   ]
  )

(defn claim-submit [params]
  [:div
   (styles/style [:div
                  [:.claim {:border-left "4px solid #888"
                            :padding-left "20px"}]
                  [:.task {:padding "15px 20px"
                               :border-radius "2px"
                               :border-left "4px solid #0275d8"}
                       [:.btn-primary {:color "#0275d8"
                                       :background-color "white"
                                       :border-color "#0275d8"}
                        [:&:hover {:background-color "#0275d8"
                                   :color "white"}]]
                       [:a.assignee {:margin-right "10px"
                                     :font-size "14px"
                                     :color "#555"}
                        [:.fa {:margin "0 5px"}]
                        [:img.avatar {:width "18px"
                                      :height "18px"
                                      :margin "0 5px"
                                      :border "1px solid #ddd"
                                      :display "inline-block"
                                      :border-radius "50%"}]]]])
   [:div.task
    [:h4.title [:b "Task:"] " Submit claims for " [:i "08/01/17"]]
    [:br]
    [:b "Start Time: "] "11:00"
    [:br]
    [:b "Status: "] "In Progress"
    [:br]
    [:b "Severity: "] "Normal"
    [:br]
    [:b "Assignee: " (user "Adolf Lungren")]
    [:a {:href "#/me"} "Assign to me"]
    " | "
    [:a {:href "#/me"} " Change"]
    [:br]
    [:br]
    [:a.btn.btn-primary {:href (href "tasks")} "Submit"]]
   [:br]
   [:div.claim
    [:table.table
     [:thead
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:th "Ticket"]
       [:th "Name"]
       [:th "Insurance"]
       [:th "Services"]]]
     [:tbody
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:td "23333f"]
       [:td "Maria Fedotova"]
       [:td "Medicare"]
       [:td {:style {:padding 0}}
        [:table.table.table-striped.table-sm
         [:thead
          [:th "date"]
          [:th "procedure"]
          [:th "diagnosis"]
          [:th "charge"]]
         [:tbody
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]]]]]
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:td "23333f"]
       [:td "Maria Fedotova"]
       [:td "Medicare"]
       [:td {:style {:padding 0}}
        [:table.table.table-striped.table-sm
         [:thead
          [:th "date"]
          [:th "procedure"]
          [:th "diagnosis"]
          [:th "charge"]]
         [:tbody
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]]]]]
      [:tr
       [:td [:input {:type "checkbox" :name "group"}]]
       [:td "23333f"]
       [:td "Maria Fedotova"]
       [:td "Medicare"]
       [:td {:style {:padding 0}}
        [:table.table.table-striped.table-sm
         [:thead
          [:th "date"]
          [:th "procedure"]
          [:th "diagnosis"]
          [:th "charge"]]
         [:tbody
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]
          [:tr
           [:td "08/01/2017"]
           [:td "88323"]
           [:td "L718, D2239"]
           [:td "170.7 x 2"]]]]]]]]]])

(defn claim-era [params]
  [:div
   (styles/style [:div
                  [:.pane {:width "900px"}]
                  [:.ctx {:position "absolute"
                          :width "400px"
                          :top 0
                          :right 0
                          :bottom 0
                          :padding "30px"
                          :border-left "1px solid #ddd"
                          :background-color "#f6f6f6"}]
                  [:.btn-primary {:color "#0275d8"
                                  :background-color "white"
                                  :border-color "#0275d8"}
                   [:&:hover {:background-color "#0275d8"
                              :color "white"}]]
                  [:.btitle {:font-size "12px"
                             :font-weight "bold"
                             :color "gray"
                             :top "-5px"
                             :position "relative"
                             :margin-bottom "10px"
                             :left "-10px"}]
                  [:h3 {:font-size "20px"}]
                  [:.era {:border-left "4px solid #F44336"
                            :padding-left "20px"}]
                  [:.task {:padding "0px 20px"
                           :border-radius "2px"
                           :border-left "4px solid #0275d8"}

                   [:b {:color "gray" :font-weight "300"}]
                   [:a.assignee {:margin-right "10px"
                                 :font-size "14px"
                                 :color "#555"}
                    [:.fa {:margin "0 5px"}]
                    [:img.avatar {:width "18px"
                                  :height "18px"
                                  :margin "0 5px"
                                  :border "1px solid #ddd"
                                  :display "inline-block"
                                  :border-radius "50%"}]]]
                  [:.patient
                   {:border-left "4px solid #03A9F4"
                    :padding-left "20px"
                    :margin-bottom "30px"}
                   [:b {:color "gray" :font-weight "300"}]
                   [:img.avatar {:width "20px" :border "1px solid #ddd" :border-radius "50%"}]]

                  [:.visit
                   {:border-left "4px solid #FFC107"
                    :padding-left "20px"
                    :margin-bottom "30px"}
                   [:b {:color "gray" :font-weight "300"}]
                   [:img.avatar {:width "18px"
                                 :height "18px"
                                 :margin "0 5px"
                                 :border "1px solid #ddd"
                                 :display "inline-block"
                                 :border-radius "50%"}]]])
   [:div.pane
    [:div.task
     [:div.btitle "task"]
     [:h3 "ERA for .... "]
     [:b "Start Time: "] "11:00"
     [:br]
     [:b "Status: "] "In Progress"
     [:br]
     [:b "Severity: "] "Normal"
     [:br]
     [:b "Assignee: " (user "Adolf Lungren")]
     [:a {:href "#/me"} "Assign to me"]
     " | "
     [:a {:href "#/me"} " Change"]
     [:br]
     [:br]
     ]
    [:br]
    [:div.era
     [:h3 "Claim was rejected"]
     [:p "Regardless of how brilliant a medical biller is, they are guaranteed to come across rejections and denials from time to time. These terms are frequently used to discuss medical billing claims and are often used interchangeably by even the most experienced team members in the health field. However, a rejection differs vastly from a denial."]

     [:h3 "Possible actions:"]
     [:a.btn.btn-primary {:href (href "tasks")} "Submit"]]]
   [:div.ctx
    [:div.patient
     [:div.btitle "patient"]
     [:h3 (avatar) " Ivan Mccalohen " [:span.small [wgt/icon :male] " 37 years old"]] 
     [:padding-left
      [:b "Phone: "] " 7777-77667"
      [:br]
      [:b "Address: "] " 1419 Westwood Blvd - Los Angeles CA"]]
    [:div.visit
     [:div.btitle "visit"]
     [:h3 "Cryotherapy" [:span.small " 08/01/2017"]] 
     [:padding-left
      [:b "Physician: "] (user "Dr. House")]]] 
   ]

  )

(defn task [params]
  [:div
   (get 
    {"claim-submit" [claim-submit params]
     "check-claim" [claim-check params]
     "claim-era" [claim-era params]
     }
    (:type params))])



(defn navigation [params]
  [:div
   [:div.navigator
    [:h3 "Navigation"]
    (styles/style [:.navigator
                   [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
                         :font-size "20px"
                         :line-height "40px"}]
                   {:border-left "4px solid #ddd" :padding-left "20px"}
                   [:.item {:display "block" :padding "5px 0" }
                    [:.fa {:width "24px"}]]])

    [:a.item {:href (href "claims")} [wgt/icon :users] " Patients"]
    [:a.item {:href (href "claims")} [wgt/icon :users] " Visits"]
    [:a.item {:href (href "claims")} [wgt/icon :dollar] " Claims"]
    [:a.item {:href (href "claims")} [wgt/icon :bar-chart] "Statistic"]
    ]

   [:div.visits
    (styles/style [:.visits
                   [:.mute {:color "#888"}]
                   [:h3 [:.fa {:font-size "18px"
                               :z-index "100"
                               :position "relative" :left "20px" :margin-left "20px" :color "gray"}]]
                   [:.search {:border-top "none"
                              :border-right "none"
                              :padding-left "24px"
                              :position "relative"
                              :bottom "-1px"
                              :width "20em"
                              :border-bottom "1px solid #888"
                              :border-left "none"}
                    [:&:focus
                     {:border-top "none"
                      :border-right "none"
                      :border-bottom "1px solid blue"
                      :border-left "none"}]
                    ]
                   [:img.avatar {:width "18px"
                                 :height "18px"
                                 :margin "0 5px"
                                 :border "1px solid #ddd"
                                 :display "inline-block"
                                 :border-radius "50%"}]
                   {:border-left "4px solid #4CAF50" :padding-left "20px" :margin-top "30px"}
                   [:h3 {:border-bottom "1px solid rgba(0,0,0,.15)!important"
                         :font-size "20px"
                         :line-height "40px"}]
                   [:.item {:display "block" :padding "5px 0" }
                    [:.fa {:width "24px"}]]])
    [:h3 "Visits  " [wgt/icon :search] [:input.search {:type "search" :placeholder "search..."}]]
    [:table.table
     [:tbody
      [:tr
       [:td.mute "08/01/17 10:00"]
       [:td (user "Luis Borhes") " " [wgt/icon :male]]
       [:td " Neurotoxins"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Joao Jilberto") " " [wgt/icon :male]]
       [:td "Neurotoxin injectable for skin rejuvenation"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Augustiniana Johanson") " " [wgt/icon :female]]
       [:td "Neurotoxin injectable for skin rejuvenation"]]
      [:tr
       [:td.mute "08/01/17 10:00"]
       [:td (user "Luis Borhes") " " [wgt/icon :male]]
       [:td " Neurotoxins"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Joao Jilberto") " " [wgt/icon :male]]
       [:td "Neurotoxin injectable for skin rejuvenation"]]
      [:tr
       [:td.mute "08/01/17 11:00"]
       [:td (user "Augustiniana Johanson") " " [wgt/icon :female]]
       [:td "Neurotoxin injectable for skin rejuvenation"]]


      ]

     ]

    ]

   ])

(defn profile [params]
  [:div
   [:h3 "Profile"]])

(pages/reg-page :dashboard/index index)
(pages/reg-page :dashboard/tasks tasks)
(pages/reg-page :dashboard/task task)
(pages/reg-page :dashboard/navigation navigation)
(pages/reg-page :dashboard/profile profile)
