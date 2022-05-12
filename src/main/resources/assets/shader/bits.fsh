#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;
in vec2 oneTexel;

uniform vec2 InSize;

//uniform float Resolution;
//uniform float Saturation;
//uniform float MosaicSize;

out vec4 fragColor;

void main() {
    vec2 mosaicInSize = InSize / 8.0;
    vec2 fractPix = fract(texCoord * mosaicInSize) / mosaicInSize;

    vec4 baseTexel = texture(DiffuseSampler, texCoord - fractPix);

    vec3 fractTexel = baseTexel.rgb - fract(baseTexel.rgb * 4.0) / 4.0;
    float luma = dot(fractTexel, vec3(0.3, 0.59, 0.11));
    vec3 chroma = (fractTexel - luma) * 1.5;
    baseTexel.rgb = luma + chroma;
    baseTexel.a = 1.0;

    fragColor = baseTexel;
}
