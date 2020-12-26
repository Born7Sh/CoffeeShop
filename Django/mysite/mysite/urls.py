"""mysite URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/3.0/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.contrib import admin
from django.conf.urls import url, include
from django.urls import path
from django.views.generic import ListView, DetailView
from rest_framework import routers
from rest_framework_swagger.views import get_swagger_view
from rest_framework.urlpatterns import format_suffix_patterns
from backend import views
from backend.views import *
from django.conf import settings
from django.conf.urls.static import static
from django.contrib.auth import views as auth_views 

#recently pics
from django.conf.urls.static import static
from django.conf import settings

#time
import datetime
"""
    
    path('Check/', include('backend.urls')),
    path('check/<int:pk>/', include('backend.urls')),
    path('timesale/', include('backend.urls')),
    path('timesale/<int:pk>/', include('backend.urls')),
    path('booking1/', include('backend.urls')),
    path('booking1/<int:pk>/', include('backend.urls')),
    path('booking2/', include('backend.urls')),
    path('booking2/<int:pk>/', include('backend.urls')),
    path('booking3/', include('backend.urls')),
    path('booking3/<int:pk>/', include('backend.urls')),
    """

#router = routers.SimpleRouter()

urlpatterns = [
    path('review/', ReviewsList.as_view()),
    path('review/<int:pk>/', ReviewsDetail.as_view()),
    path('notice/', NoticesList.as_view()),
    path('notice/<int:pk>/', NoticesDetail.as_view()),
    path('inquiry/', InquirysList.as_view()),
    path('inquiry/<int:pk>/', InquirysDetail.as_view()),
    path('cafe/', CafesList.as_view()),
    path('cafe/<int:pk>/', CafesDetail.as_view()),
    path('check/', ChecksList.as_view()),
    path('check/<int:pk>/', ChecksDetail.as_view()),
    path('age/', AgesList.as_view()),
    path('age/<int:pk>/', AgesDetail.as_view()),
    path('gender/', GendersList.as_view()),
    path('gender/<int:pk>/', GendersDetail.as_view()),

    path('timesale/', TimeSalesList.as_view()),
    path('timesale/<int:pk>/', TimeSalesDetail.as_view()),
    path('booking1/', Booking1sList.as_view()),
    path('booking1/<int:pk>/', Booking1sDetail.as_view()),
    path('booking2/', Booking2sList.as_view()),
    path('booking2/<int:pk>/', Booking2sDetail.as_view()),
    path('booking3/', Booking3sList.as_view()),
    path('booking3/<int:pk>/', Booking3sDetail.as_view()),
    path('img/', ImgsList.as_view()),
    path('img/<int:pk>/', ImgsDetail.as_view()),
    path('favorite/', FavoritesList.as_view()),
    path('favorite/<int:pk>/', FavoritesDetail.as_view()),
    path('guest/', GuestsList.as_view()),
    path('guest/<int:pk>/', GuestsDetail.as_view()),
    path('admin/', admin.site.urls),
    path('api/doc/', get_swagger_view(title='API')),
    path('accounts/resend_verify_email/', ResendVerifyEmailView.as_view()),
    path('accounts/',include('django.contrib.auth.urls')),
    path('accounts/register/',UserCreateView.as_view(),name='register'),
    path('user/<pk>/verify/<token>/', UserVerificationView.as_view()),
    url('^cafe/(?P<cafe_name>.+)/$', CafeListView.as_view()),
    url('^check/(?P<uid>.+)/$', CheckListView.as_view()),
    url('^favorite/(?P<uid>.+)/$', FavoriteListView.as_view()),
    url('^review/(?P<uid>.+)/$', ReviewListView.as_view()),
    url('^timesale/(?P<cno>.+)/$', TimeSaleListView.as_view()),
    url('^booking1/(?P<cno>.+)/$', Booking1ListView.as_view()),
    url('^booking2/(?P<cno>.+)/$', Booking2ListView.as_view()),
    url('^booking3/(?P<cno>.+)/$', Booking3ListView.as_view()),
    url('^img/(?P<img>.+)/$', ImgListView.as_view()),
#
    path('accounts/resend_verify_emailm/', ResendVerifyEmailView1.as_view()),
    path('accounts/registerm/',UserCreateView1.as_view(),name='register'),
    path('accounts/register/14gram/', GramView.as_view()),
    path('accounts/register/private/', PrivateView.as_view()),
    path('accounts/register/location/', LocationView.as_view()),
    path('accounts/registerm/14gram/', GramView.as_view()),
    path('accounts/registerm/private/', PrivateView.as_view()),
    path('accounts/registerm/location/', LocationView.as_view()),
    path('accounts/password_reset/done/', auth_views.PasswordResetDoneView.as_view(template_name='main/password/password_reset_done.html'), name='password_reset_done'),
    path('accounts/reset/<uidb64>/<token>/', auth_views.PasswordResetConfirmView.as_view(template_name="main/password/password_reset_confirm.html"), name='password_reset_confirm'),
    path('accounts/reset/done/', auth_views.PasswordResetCompleteView.as_view(template_name='main/password/password_reset_complete.html'), name='password_reset_complete'),  
    path('accounts/password_reset/', views.password_reset_request, name="password_reset"),
    path('manager/<pk>/verify/<token>/', UserVerificationView1.as_view()),
    path('accounts/registerm/done/',UserCreateDoneTV1.as_view(),name='register_done'),
#
    path('accounts/register/done/',UserCreateDoneTV.as_view(),name='register_done'),
    path('accounts/pwchange/',auth_views.PasswordChangeView.as_view(),name='password_change_form'),
    path('accounts/pwchange/done/',auth_views.PasswordChangeDoneView.as_view(),name='password_change_done'),
    path('app_login/',views.app_login),
    path('app_login/',views.reservation),
    path('app_loginm/',views.app_loginm),
    path('get_time/',views.get_time),
    path('time/',views.reservation),
    path('recommendation/',views.recommendation),
    path('tag/',views.tag),
    path('openclose/',views.openclose),
    path('age_stats/',views.age_stats),
    path('gender_stats/',views.gender_stats),
    path('star/',views.star),
    path('',HomeView.as_view(), name='home'),
    path('apk/',ApkDownloadView.as_view()),
] + static(settings.MEDIA_URL, document_root=settings.MEDIA_ROOT)

#recently pics
urlpatterns += static(settings.MEDIA_URL,document_root=settings.MEDIA_ROOT) 
urlpatterns += static(settings.STATIC_URL, document_root=settings.STATIC_ROOT)