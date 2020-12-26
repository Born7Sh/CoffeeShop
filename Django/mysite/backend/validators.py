from django.contrib.auth import get_user_model
from django.core.exceptions import ValidationError


class RegisteredEmailValidator:
    user_model = get_user_model()
    code = 'invalid'

    def __call__(self, email):
        try:
            user = self.user_model.objects.get(email=email)
        except self.user_model.DoesNotExist:
            raise ValidationError('Email not subscribed.', code=self.code)
        else:
            if user.is_active:
                raise ValidationError('You are already authenticated.', code=self.code)

        return