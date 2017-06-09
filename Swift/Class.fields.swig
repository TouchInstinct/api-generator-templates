{%- import 'swift-utils.twig' as utils -%}

{%- if fields is not empty -%}
// MARK: - Fields
{% for field in fields %}
    /// {{ field.description }}
    let {{ field.name }}: {{ utils.formatNullable(utils.formatFieldValueType(field.type.type), field.nullable) }}
{% endfor -%}
{% endif %}