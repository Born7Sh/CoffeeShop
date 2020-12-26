from django.urls import path
from rest_framework.urlpatterns import format_suffix_patterns
from .views import *
from . import views
from rest_framework.routers import DefaultRouter

router = DefaultRouter()
router.register('review',views.ReviewViewSet)
router.register('cafe',views.CafeViewSet)
router.register('check',views.CheckViewSet)
router.register('timesale',views.Time_SaleViewSet)
router.register('Booking1',views.Booking1ViewSet)
router.register('Booking2',views.Booking2ViewSet)
router.register('Booking3',views.Booking3ViewSet)

"""
    path('review', ReviewList.as_view()),
    path('review/<int:pk>/', ReviewDetail.as_view()),
    path('cafe/', CafeList.as_view()),
    path('cafe/<int:pk>/', CafeDetail.as_view()),
    path('check/', CheckList.as_view()),
    path('check/<int:pk>/', CheckDetail.as_view()),
    path('timesale/', Time_SaleList.as_view()),
    path('timesale/<int:pk>/', Time_SaleDetail.as_view()),
    path('booking1/', Booking1List.as_view()),
    path('booking1/<int:pk>/', Booking1Detail.as_view()),
    path('booking2/', Booking2List.as_view()),
    path('booking2/<int:pk>/', Booking2Detail.as_view()),
    path('booking3/', Booking3List.as_view()),
    path('booking3/<int:pk>/', Bookin3Detail.as_view()),
"""

urlpatterns = [
    
]
