#!/usr/bin/env python
#
# Copyright 2007 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
import webapp2
from google.appengine.ext import db

class OptionModle(db.Model):
    option = db.StringProperty()
    weight = db.IntegerProperty()

class VoteModel(db.Model):
    vote_id = db.IntegerProperty()
    topic = db.StringProperty()
    launcher = db.StringProperty()
    description = db.StringProperty()
    single = db.BooleanProperty()
    has_password = db.BooleanProperty()
    password = db.StringProperty()
    left_time = db.IntegerProperty()
    options = db.ListProperty(db.Key)

class MainHandler(webapp2.RequestHandler):
    def get(self):
        self.response.write('Hello world!')


class Launch(webapp2.RequestHandler):
    def get(self):
        self.post()
        self.response.write('ERROR: HTTP-GET Is Unacceptable')
    def post(self):
        votes = VoteModel();
        votes.vote_id = db.GqlQuery("SELECT * FROM VoteModel").count() + 1
        votes.topic = self.request.get('topic')
        votes.launcher = self.request.get('launcher')
        votes.description = self.request.get('description')
        votes.single = (self.request.get('type') == 'single')
        votes.password = self.request.get('password')
        votes.has_password = (votes.password != "")
        left_time = self.request.get('left_time')
        options = self.request.get('option',allow_multiple=True)
        if len(options) == 0:
            self.response.out.write("ERROR: Empty Option List")
            return
        for item in options:
            # self.response.write(item)
            new_option = OptionModle(option = str(item), weight = 0)
            new_option.put()
            votes.options.append(new_option.key())
        votes.put()
        self.response.write("%s" % votes.vote_id)

class RequestById(webapp2.RequestHandler):
    def get(self):
        request_id = self.request.get("id")
        request_result = db.GqlQuery("SELECT * FROM VoteModel WHERE vote_id = %s" % request_id)
        self.response.out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n")
        if request_result.count() != 0:
            request_vote = request_result.get()
            self.response.out.write("<Id>%d</Id>\n" % request_vote.vote_id)
            self.response.out.write("<Topic>%s</Topic>\n" % request_vote.topic)
            self.response.out.write("<Launcher>%s</Launcher>\n" % request_vote.launcher)
            self.response.out.write("<Description>%s</Description>\n" % request_vote.description)
            if request_vote.single:
                self.response.out.write("<Type>%s</Type>\n" % "single")
            else:
                self.response.out.write("<Type>%s</Type>\n" % "multi")
            self.response.out.write("<Password>%s</Password>\n" % request_vote.password)
            #self.response.out.write("<LeftTime>%s</LeftTime>\n" % request_vote.left_time)
            for key in request_vote.options:
                options = db.get(key)
                self.response.out.write("<Option weight = \"%d\">%s</Option>\n" % (options.weight, options.option))
        else:
            self.response.out.write("<Error>Invalid Id</Error>\n")
    def post(self):
        self.response.out.write('ERROR: HTTP-POST Is Unacceptable')

class Vote(webapp2.RequestHandler):
    def get(self):
        self.post()
        self.response.write('ERROR: HTTP-GET Is Unacceptable')
    def post(self):
        request_id = self.request.get("id")
        request_result = db.GqlQuery("SELECT * FROM VoteModel WHERE vote_id = %s" % request_id)
        if request_result.count() != 0:
            request_vote = request_result.get()
            vote_options = self.request.get('option',allow_multiple=True)
            # TODO: add type check
            for index in vote_options:
                options = db.get(request_vote.options[int(index)])
                options.weight += 1
                options.put()
        else:
            self.response.out.write('ERROR: Invalid Id')

app = webapp2.WSGIApplication([
    ('/', MainHandler),
    ('/launch', Launch),
    ('/request', RequestById),
    ('/vote', Vote)
], debug=True)
