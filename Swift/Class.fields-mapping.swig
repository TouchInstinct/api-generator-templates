{%- import 'swift-utils.twig' as utils -%}
        {%- for field in fields %}
        {{ field.name }} = {{ utils.formatNullable('try', field.nullable) }} {{ utils.mappingForField(field, className) }}
{%- endfor -%}