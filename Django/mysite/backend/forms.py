from django import forms
from django.db import models
from user.models import *
from django.forms import EmailField
from .validators import *


from django.contrib.auth import get_user_model
from django.contrib.auth.forms import UserCreationForm


class UserCreationForm(UserCreationForm):
    class Meta:
        model = get_user_model()
        fields = ('email','password1','password2','name','nickname','phone','address','birth_day','gender','image',)
"""
class UserCreationForm(UserCreationForm):
    class Meta:
        model = get_user_model()
        fields = ('email', 'name')
"""
class VerificationEmailForm(forms.Form):
    email = EmailField(widget=forms.EmailInput(attrs={'autofocus': True}), validators=(EmailField.default_validators + [RegisteredEmailValidator()]))

