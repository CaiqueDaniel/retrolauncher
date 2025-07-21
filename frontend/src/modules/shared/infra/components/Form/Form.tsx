import { Box } from '@mui/material';
import { Formik, Form as FormikForm } from 'formik';

export function Form<I extends Record<string, any>>({
  children,
  initialValues,
  validationSchema,
  onSubmit,
}: Props<I>) {
  return (
    <Formik<I>
      initialValues={initialValues}
      onSubmit={onSubmit}
      validationSchema={validationSchema}
      isInitialValid
    >
      {({ errors, setFieldValue }) => (
        <FormikForm>
          <Box>
            {children({
              errors: errors as Record<keyof I, string>,
              setFieldValue: (field: string, value: any) =>
                setFieldValue(field, value, true),
            })}
          </Box>
        </FormikForm>
      )}
    </Formik>
  );
}

type Props<I> = {
  initialValues: I;
  onSubmit: (values: I) => void;
  validationSchema?: any;
  children: (props: {
    errors: Record<keyof I, string>;
    setFieldValue: (field: string, value: any) => void;
  }) => React.ReactNode;
};
