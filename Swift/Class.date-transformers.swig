{%- import 'swift-utils.twig' as utils -%}

{%- set hasDateFields = false -%}

{%- for field in fields -%}
    {%- if field.type.type.baseTypeName == "DateTime" -%}
        {%- set hasDateFields = true -%}
    {%- endif -%}
{%- endfor -%}

{%- if hasDateFields -%}
    // MARK: - Date transformers
    {%- for field in fields %}
    {% if field.type.type.baseTypeName == "DateTime" -%}
    static let {{ field.name }}Transform = ApiDateFormattingService.shared.mappingTransform(for: "{{ field.type.dateFormat }}")
    {% endif -%}
    {% endfor %}
{%- endif -%}