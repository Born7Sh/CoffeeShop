# Generated by Django 3.1 on 2020-09-17 13:33

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('backend', '0002_check_nickname'),
    ]

    operations = [
        migrations.AddField(
            model_name='cafe',
            name='event',
            field=models.TextField(default=1, verbose_name='이벤트'),
            preserve_default=False,
        ),
    ]