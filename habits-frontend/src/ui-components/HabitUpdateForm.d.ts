/***************************************************************************
 * The contents of this file were generated with Amplify Studio.           *
 * Please refrain from making any modifications to this file.              *
 * Any changes to this file will be overwritten when running amplify pull. *
 **************************************************************************/

import * as React from "react";
import { GridProps, TextFieldProps } from "@aws-amplify/ui-react";
import { Habit } from "../models";
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
export declare type HabitUpdateFormInputValues = {
    Name?: string;
    Description?: string;
};
export declare type HabitUpdateFormValidationValues = {
    Name?: ValidationFunction<string>;
    Description?: ValidationFunction<string>;
};
export declare type PrimitiveOverrideProps<T> = Partial<T> & React.DOMAttributes<HTMLDivElement>;
export declare type HabitUpdateFormOverridesProps = {
    HabitUpdateFormGrid?: PrimitiveOverrideProps<GridProps>;
    Name?: PrimitiveOverrideProps<TextFieldProps>;
    Description?: PrimitiveOverrideProps<TextFieldProps>;
} & EscapeHatchProps;
export declare type HabitUpdateFormProps = React.PropsWithChildren<{
    overrides?: HabitUpdateFormOverridesProps | undefined | null;
} & {
    id?: string;
    habit?: Habit;
    onSubmit?: (fields: HabitUpdateFormInputValues) => HabitUpdateFormInputValues;
    onSuccess?: (fields: HabitUpdateFormInputValues) => void;
    onError?: (fields: HabitUpdateFormInputValues, errorMessage: string) => void;
    onChange?: (fields: HabitUpdateFormInputValues) => HabitUpdateFormInputValues;
    onValidate?: HabitUpdateFormValidationValues;
} & React.CSSProperties>;
export default function HabitUpdateForm(props: HabitUpdateFormProps): React.ReactElement;
