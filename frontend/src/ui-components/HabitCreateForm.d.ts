/***************************************************************************
 * The contents of this file were generated with Amplify Studio.           *
 * Please refrain from making any modifications to this file.              *
 * Any changes to this file will be overwritten when running amplify pull. *
 **************************************************************************/

import * as React from "react";
import { GridProps, TextFieldProps } from "@aws-amplify/ui-react";
export declare type EscapeHatchProps = {
    [elementHierarchy: string]: Record<string, unknown>;
} | null;
export declare type VariantValues = {
    [key: string]: string;
};
export declare type Variant = {
    variantValues: VariantValues;
    overrides: EscapeHatchProps;
};
export declare type ValidationResponse = {
    hasError: boolean;
    errorMessage?: string;
};
export declare type ValidationFunction<T> = (value: T, validationResponse: ValidationResponse) => ValidationResponse | Promise<ValidationResponse>;
export declare type HabitCreateFormInputValues = {
    Name?: string;
    Description?: string;
};
export declare type HabitCreateFormValidationValues = {
    Name?: ValidationFunction<string>;
    Description?: ValidationFunction<string>;
};
export declare type PrimitiveOverrideProps<T> = Partial<T> & React.DOMAttributes<HTMLDivElement>;
export declare type HabitCreateFormOverridesProps = {
    HabitCreateFormGrid?: PrimitiveOverrideProps<GridProps>;
    Name?: PrimitiveOverrideProps<TextFieldProps>;
    Description?: PrimitiveOverrideProps<TextFieldProps>;
} & EscapeHatchProps;
export declare type HabitCreateFormProps = React.PropsWithChildren<{
    overrides?: HabitCreateFormOverridesProps | undefined | null;
} & {
    clearOnSuccess?: boolean;
    onSubmit?: (fields: HabitCreateFormInputValues) => HabitCreateFormInputValues;
    onSuccess?: (fields: HabitCreateFormInputValues) => void;
    onError?: (fields: HabitCreateFormInputValues, errorMessage: string) => void;
    onChange?: (fields: HabitCreateFormInputValues) => HabitCreateFormInputValues;
    onValidate?: HabitCreateFormValidationValues;
} & React.CSSProperties>;
export default function HabitCreateForm(props: HabitCreateFormProps): React.ReactElement;
