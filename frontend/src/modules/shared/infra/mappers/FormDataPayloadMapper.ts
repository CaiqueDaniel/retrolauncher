import { PayloadMapper } from '../../application/PayloadMapper';

export class FormDataPayloadMapper implements PayloadMapper<FormData> {
  transform(input: Record<string, any>): FormData {
    const formData = new FormData();

    this.parseObject(input, formData);

    return formData;
  }

  private parseObject(
    input: Record<string, any>,
    formData: FormData,
    previousPathname?: string
  ) {
    Object.keys(input).forEach((key) => {
      let value: string | File;
      let pathname = previousPathname ?? '';
      pathname += previousPathname ? `[${key}]` : key;

      if (!input[key]) return;
      else if (input[key] instanceof Date) value = input[key].toISOString();
      else if (input[key] instanceof File) value = input[key];
      else if (input[key] instanceof Object) {
        this.parseObject(input[key], formData, pathname);
        return;
      } else value = input[key];

      formData.set(pathname, value);
    });
  }
}
