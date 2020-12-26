
from django.contrib.auth.models import (
    AbstractBaseUser, PermissionsMixin, UserManager
)
from django.db import models
from django.utils import timezone
from django.utils.translation import ugettext_lazy as _


class User(AbstractBaseUser, PermissionsMixin):
    email = models.EmailField('email', unique=True)
    name = models.CharField('name', max_length=30, blank=True)
    phone = models.CharField(max_length=11, null=True)
    address = models.CharField(max_length=70, null=True)
    birth_day = models.DateField(null=True)
    nickname = models.CharField('nickname', max_length=30, blank=True)
    cno = models.IntegerField(verbose_name='cno',blank=True,null=True)
    r1 = models.IntegerField(verbose_name='r1',blank=True,null=True)
    r2 = models.IntegerField(verbose_name='r2',blank=True,null=True)
    r3 = models.IntegerField(verbose_name='r3',blank=True,null=True)
    r4 = models.IntegerField(verbose_name='r4',blank=True,null=True)
    r5 = models.IntegerField(verbose_name='r5',blank=True,null=True)
    r6 = models.IntegerField(verbose_name='r6',blank=True,null=True)
    gender = models.CharField(max_length=6, null=True)
    image = models.ImageField(blank=False)
    image2 = models.ImageField(blank=False)
    is_wait = models.BooleanField('wait',default=False)  
    is_staff = models.BooleanField('staff', default=False)
    is_active = models.BooleanField('active', default=False)
    date_joined = models.DateTimeField('date', default=timezone.now)

    objects = UserManager()
    
    USERNAME_FIELD = 'email'
    REQUIRED_FIELDS = ['name']

    class Meta:
        verbose_name = _('user')
        verbose_name_plural = _('users')
        swappable = 'AUTH_USER_MODEL'

    def email_user(self, subject, message, from_email=None, **kwargs):
        send_mail(subject, message, from_email, [self.email], **kwargs)

    
class Guest(models.Model):
    nickname = models.CharField('nickname', max_length=30, blank=True)
    phone = models.CharField(max_length=20, null=True)
    date_joined = models.DateTimeField('date', default=timezone.now)



from django.contrib.auth.base_user import AbstractBaseUser, BaseUserManager
from django.contrib.auth.models import PermissionsMixin
from django.core.mail import send_mail
from django.db import models
from django.utils import timezone

class UserManager(BaseUserManager):
    use_in_migrations = True

    def _create_user(self, email, password, **extra_fields):
        if not email:
            raise ValueError('The given email must be set')
        email = self.normalize_email(email)
        user = self.model(email=email, **extra_fields)
        user.set_password(password)
        user.save(using=self._db)
        return user

    def create_user(self, email=None, password=None, **extra_fields):
        extra_fields.setdefault('is_staff', False)
        extra_fields.setdefault('is_superuser', False)
        return self._create_user(email, password, **extra_fields)

    def create_superuser(self, email, password, **extra_fields):
        extra_fields.setdefault('is_staff', True)
        extra_fields.setdefault('is_superuser', True)

        if extra_fields.get('is_staff') is not True:
            raise ValueError('Superuser must have is_staff=True.')
        if extra_fields.get('is_superuser') is not True:
            raise ValueError('Superuser must have is_superuser=True.')

        return self._create_user(email, password, **extra_fields)

