# Generated by Django 3.1 on 2020-10-10 08:02

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('user', '0006_auto_20201008_1129'),
    ]

    operations = [
        migrations.AddField(
            model_name='user',
            name='cno',
            field=models.IntegerField(blank=True, null=True, verbose_name='cno'),
        ),
    ]
