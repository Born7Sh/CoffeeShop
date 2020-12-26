from django.contrib import messages
from django.contrib.auth.tokens import default_token_generator
from django.shortcuts import render

from mysite import settings


class VerifyEmailMixin:
    email_template_name = 'user/email/registration_verification.html'
    token_generator = default_token_generator

    def send_verification_email(self, user):
        token = self.token_generator.make_token(user)
        url = self.build_verification_link(user, token)
        subject = 'Congratulations on your registration.!!!'
        message = 'Please go to the following address to authenticate.. {}'.format(url)
        html_message = render(self.request, self.email_template_name, {'url': url}).content.decode('utf-8')
        user.email_user(subject, message, from_email=settings.EMAIL_HOST_USER,html_message=html_message)
        messages.info(self.request, 'Congratulations on your registration.!!! We sent the authentication email to the email address you signed up for, so please confirm after checking it.')

    def build_verification_link(self, user, token):
        return '{}/user/{}/verify/{}/'.format(self.request.META.get('HTTP_ORIGIN'), user.pk, token)