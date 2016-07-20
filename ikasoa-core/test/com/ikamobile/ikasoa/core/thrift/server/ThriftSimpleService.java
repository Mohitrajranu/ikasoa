/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.ikamobile.ikasoa.core.thrift.server;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import javax.annotation.Generated;

/**
 * 用于测试的简化版Thrift服务
 * <p>
 * 在IDL生成的Service基础上修改简化而成.
 * <p>
 * 只包含一个<code>get</code>
 * 方法,参数与返回值都为字符串,可以将对象转换为(json或xml等)字符串进行操作,这样可以避免使用IDL,灵活性更高.
 */
@SuppressWarnings({ "cast", "rawtypes", "serial", "unchecked" })
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)")
public class ThriftSimpleService {

	public interface Iface {

		public String get(String arg) throws org.apache.thrift.TException;

	}

	public static class Client extends org.apache.thrift.TServiceClient implements Iface {
		public static class Factory implements org.apache.thrift.TServiceClientFactory<Client> {
			public Factory() {
				// Do nothing
			}

			public Client getClient(org.apache.thrift.protocol.TProtocol prot) {
				return new Client(prot);
			}

			public Client getClient(org.apache.thrift.protocol.TProtocol iprot,
					org.apache.thrift.protocol.TProtocol oprot) {
				return new Client(iprot, oprot);
			}
		}

		public Client(org.apache.thrift.protocol.TProtocol prot) {
			super(prot, prot);
		}

		public Client(org.apache.thrift.protocol.TProtocol iprot, org.apache.thrift.protocol.TProtocol oprot) {
			super(iprot, oprot);
		}

		@Override
		public String get(String arg) throws org.apache.thrift.TException {
			send_get(arg);
			return recv_get();
		}

		public void send_get(String arg) throws org.apache.thrift.TException {
			get_args args = new get_args();
			args.setArg(arg);
			sendBase("get", args);
		}

		public String recv_get() throws org.apache.thrift.TException {
			get_result result = new get_result();
			receiveBase(result, "get");
			if (result.isSetSuccess()) {
				return result.success;
			}
			throw new org.apache.thrift.TApplicationException(org.apache.thrift.TApplicationException.MISSING_RESULT,
					"get failed: unknown result");
		}

	}

	public static class Processor<I extends Iface> extends org.apache.thrift.TBaseProcessor<I>
			implements org.apache.thrift.TProcessor {
		public Processor(I iface) {
			super(iface, getProcessMap(
					new HashMap<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>>()));
		}

		protected Processor(I iface,
				Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
			super(iface, getProcessMap(processMap));
		}

		private static <I extends Iface> Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> getProcessMap(
				Map<String, org.apache.thrift.ProcessFunction<I, ? extends org.apache.thrift.TBase>> processMap) {
			processMap.put("get", new get());
			return processMap;
		}

		public static class get<I extends Iface> extends org.apache.thrift.ProcessFunction<I, get_args> {
			public get() {
				super("get");
			}

			public get_args getEmptyArgsInstance() {
				return new get_args();
			}

			protected boolean isOneway() {
				return false;
			}

			public get_result getResult(I iface, get_args args) throws org.apache.thrift.TException {
				get_result result = new get_result();
				result.success = iface.get(args.arg);
				return result;
			}
		}

	}

	public static class get_args implements org.apache.thrift.TBase<get_args, get_args._Fields>, java.io.Serializable,
			Cloneable, Comparable<get_args> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"get_args");

		private static final org.apache.thrift.protocol.TField ARG_FIELD_DESC = new org.apache.thrift.protocol.TField(
				"arg", org.apache.thrift.protocol.TType.STRING, (short) 1);

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<>();

		static {
			schemes.put(StandardScheme.class, new get_argsStandardSchemeFactory());
			schemes.put(TupleScheme.class, new get_argsTupleSchemeFactory());
		}

		public String arg; // required

		/**
		 * The set of fields this struct contains, along with convenience
		 * methods for finding and manipulating them.
		 */
		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			ARG((short) 1, "arg");

			private static final Map<String, _Fields> byName = new HashMap<>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			/**
			 * Find the _Fields constant that matches fieldId, or null if its
			 * not found.
			 */
			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				case 1: // ARG
					return ARG;
				default:
					return null;
				}
			}

			/**
			 * Find the _Fields constant that matches fieldId, throwing an
			 * exception if it is not found.
			 */
			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
				return fields;
			}

			/**
			 * Find the _Fields constant that matches name, or null if its not
			 * found.
			 */
			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		// isset id assignments
		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			tmpMap.put(_Fields.ARG, new org.apache.thrift.meta_data.FieldMetaData("arg",
					org.apache.thrift.TFieldRequirementType.DEFAULT,
					new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(get_args.class, metaDataMap);
		}

		public get_args() {
			// Do nothing
		}

		public get_args(String arg) {
			this();
			this.arg = arg;
		}

		/**
		 * Performs a deep copy on <i>other</i>.
		 */
		public get_args(get_args other) {
			if (other.isSetArg()) {
				this.arg = other.arg;
			}
		}

		public get_args deepCopy() {
			return new get_args(this);
		}

		@Override
		public void clear() {
			this.arg = null;
		}

		public String getArg() {
			return this.arg;
		}

		public get_args setArg(String arg) {
			this.arg = arg;
			return this;
		}

		public void unsetArg() {
			this.arg = null;
		}

		/**
		 * Returns true if field arg is set (has been assigned a value) and
		 * false otherwise
		 */
		public boolean isSetArg() {
			return this.arg != null;
		}

		public void setArgIsSet(boolean value) {
			if (!value) {
				this.arg = null;
			}
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			case ARG:
				if (value == null) {
					unsetArg();
				} else {
					setArg((String) value);
				}
				break;
			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			case ARG:
				return getArg();
			}
			throw new IllegalStateException();
		}

		/**
		 * Returns true if field corresponding to fieldID is set (has been
		 * assigned a value) and false otherwise
		 */
		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			case ARG:
				return isSetArg();
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof get_args)
				return this.equals((get_args) that);
			return false;
		}

		public boolean equals(get_args that) {
			if (that == null)
				return false;

			boolean this_present_arg = this.isSetArg();
			boolean that_present_arg = that.isSetArg();
			if (this_present_arg || that_present_arg) {
				if (!(this_present_arg && that_present_arg))
					return false;
				if (!this.arg.equals(that.arg))
					return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			List<Object> list = new ArrayList<>();

			boolean present_arg = isSetArg();
			list.add(present_arg);
			if (present_arg)
				list.add(arg);

			return list.hashCode();
		}

		@Override
		public int compareTo(get_args other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(other.getClass().getName());
			}

			int lastComparison = 0;

			lastComparison = Boolean.valueOf(isSetArg()).compareTo(other.isSetArg());
			if (lastComparison != 0) {
				return lastComparison;
			}
			if (isSetArg()) {
				lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.arg, other.arg);
				if (lastComparison != 0) {
					return lastComparison;
				}
			}
			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("get_args(");

			sb.append("arg:");
			if (this.arg == null) {
				sb.append("null");
			} else {
				sb.append(this.arg);
			}
			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
			try {
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class get_argsStandardSchemeFactory implements SchemeFactory {
			public get_argsStandardScheme getScheme() {
				return new get_argsStandardScheme();
			}
		}

		private static class get_argsStandardScheme extends StandardScheme<get_args> {

			public void read(org.apache.thrift.protocol.TProtocol iprot, get_args struct)
					throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					case 1: // ARG
						if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
							struct.arg = iprot.readString();
							struct.setArgIsSet(true);
						} else {
							org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
						}
						break;
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot, get_args struct)
					throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				if (struct.arg != null) {
					oprot.writeFieldBegin(ARG_FIELD_DESC);
					oprot.writeString(struct.arg);
					oprot.writeFieldEnd();
				}
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class get_argsTupleSchemeFactory implements SchemeFactory {
			public get_argsTupleScheme getScheme() {
				return new get_argsTupleScheme();
			}
		}

		private static class get_argsTupleScheme extends TupleScheme<get_args> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot, get_args struct)
					throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
				BitSet optionals = new BitSet();
				if (struct.isSetArg()) {
					optionals.set(0);
				}
				oprot.writeBitSet(optionals, 1);
				if (struct.isSetArg()) {
					oprot.writeString(struct.arg);
				}
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot, get_args struct)
					throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
				BitSet incoming = iprot.readBitSet(1);
				if (incoming.get(0)) {
					struct.arg = iprot.readString();
					struct.setArgIsSet(true);
				}
			}
		}

	}

	public static class get_result implements org.apache.thrift.TBase<get_result, get_result._Fields>,
			java.io.Serializable, Cloneable, Comparable<get_result> {
		private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct(
				"get_result");

		private static final org.apache.thrift.protocol.TField SUCCESS_FIELD_DESC = new org.apache.thrift.protocol.TField(
				"success", org.apache.thrift.protocol.TType.STRING, (short) 0);

		private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<>();

		static {
			schemes.put(StandardScheme.class, new get_resultStandardSchemeFactory());
			schemes.put(TupleScheme.class, new get_resultTupleSchemeFactory());
		}

		public String success; // required

		/**
		 * The set of fields this struct contains, along with convenience
		 * methods for finding and manipulating them.
		 */
		public enum _Fields implements org.apache.thrift.TFieldIdEnum {
			SUCCESS((short) 0, "success");

			private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

			static {
				for (_Fields field : EnumSet.allOf(_Fields.class)) {
					byName.put(field.getFieldName(), field);
				}
			}

			/**
			 * Find the _Fields constant that matches fieldId, or null if its
			 * not found.
			 */
			public static _Fields findByThriftId(int fieldId) {
				switch (fieldId) {
				case 0: // SUCCESS
					return SUCCESS;
				default:
					return null;
				}
			}

			/**
			 * Find the _Fields constant that matches fieldId, throwing an
			 * exception if it is not found.
			 */
			public static _Fields findByThriftIdOrThrow(int fieldId) {
				_Fields fields = findByThriftId(fieldId);
				if (fields == null)
					throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
				return fields;
			}

			/**
			 * Find the _Fields constant that matches name, or null if its not
			 * found.
			 */
			public static _Fields findByName(String name) {
				return byName.get(name);
			}

			private final short _thriftId;
			private final String _fieldName;

			_Fields(short thriftId, String fieldName) {
				_thriftId = thriftId;
				_fieldName = fieldName;
			}

			public short getThriftFieldId() {
				return _thriftId;
			}

			public String getFieldName() {
				return _fieldName;
			}
		}

		// isset id assignments
		public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

		static {
			Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(
					_Fields.class);
			tmpMap.put(_Fields.SUCCESS, new org.apache.thrift.meta_data.FieldMetaData("success",
					org.apache.thrift.TFieldRequirementType.DEFAULT,
					new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
			metaDataMap = Collections.unmodifiableMap(tmpMap);
			org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(get_result.class, metaDataMap);
		}

		public get_result() {
		}

		public get_result(String success) {
			this();
			this.success = success;
		}

		/**
		 * Performs a deep copy on <i>other</i>.
		 */
		public get_result(get_result other) {
			if (other.isSetSuccess()) {
				this.success = other.success;
			}
		}

		public get_result deepCopy() {
			return new get_result(this);
		}

		@Override
		public void clear() {
			this.success = null;
		}

		public String getSuccess() {
			return this.success;
		}

		public get_result setSuccess(String success) {
			this.success = success;
			return this;
		}

		public void unsetSuccess() {
			this.success = null;
		}

		/**
		 * Returns true if field success is set (has been assigned a value) and
		 * false otherwise
		 */
		public boolean isSetSuccess() {
			return this.success != null;
		}

		public void setSuccessIsSet(boolean value) {
			if (!value) {
				this.success = null;
			}
		}

		public void setFieldValue(_Fields field, Object value) {
			switch (field) {
			case SUCCESS:
				if (value == null) {
					unsetSuccess();
				} else {
					setSuccess((String) value);
				}
				break;

			}
		}

		public Object getFieldValue(_Fields field) {
			switch (field) {
			case SUCCESS:
				return getSuccess();

			}
			throw new IllegalStateException();
		}

		/**
		 * Returns true if field corresponding to fieldID is set (has been
		 * assigned a value) and false otherwise
		 */
		public boolean isSet(_Fields field) {
			if (field == null) {
				throw new IllegalArgumentException();
			}

			switch (field) {
			case SUCCESS:
				return isSetSuccess();
			}
			throw new IllegalStateException();
		}

		@Override
		public boolean equals(Object that) {
			if (that == null)
				return false;
			if (that instanceof get_result)
				return this.equals((get_result) that);
			return false;
		}

		public boolean equals(get_result that) {
			if (that == null)
				return false;

			boolean this_present_success = this.isSetSuccess();
			boolean that_present_success = that.isSetSuccess();
			if (this_present_success || that_present_success) {
				if (!(this_present_success && that_present_success))
					return false;
				if (!this.success.equals(that.success))
					return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			List<Object> list = new ArrayList<>();

			boolean present_success = isSetSuccess();
			list.add(present_success);
			if (present_success)
				list.add(success);

			return list.hashCode();
		}

		@Override
		public int compareTo(get_result other) {
			if (!getClass().equals(other.getClass())) {
				return getClass().getName().compareTo(other.getClass().getName());
			}

			int lastComparison = 0;

			lastComparison = Boolean.valueOf(isSetSuccess()).compareTo(other.isSetSuccess());
			if (lastComparison != 0) {
				return lastComparison;
			}
			if (isSetSuccess()) {
				lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.success, other.success);
				if (lastComparison != 0) {
					return lastComparison;
				}
			}
			return 0;
		}

		public _Fields fieldForId(int fieldId) {
			return _Fields.findByThriftId(fieldId);
		}

		public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
			schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
		}

		public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
			schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder("get_result(");

			sb.append("success:");
			if (this.success == null) {
				sb.append("null");
			} else {
				sb.append(this.success);
			}
			sb.append(")");
			return sb.toString();
		}

		public void validate() throws org.apache.thrift.TException {
			// check for required fields
			// check for sub-struct validity
		}

		private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
			try {
				write(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(out)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
			try {
				read(new org.apache.thrift.protocol.TCompactProtocol(
						new org.apache.thrift.transport.TIOStreamTransport(in)));
			} catch (org.apache.thrift.TException te) {
				throw new java.io.IOException(te);
			}
		}

		private static class get_resultStandardSchemeFactory implements SchemeFactory {
			public get_resultStandardScheme getScheme() {
				return new get_resultStandardScheme();
			}
		}

		private static class get_resultStandardScheme extends StandardScheme<get_result> {

			public void read(org.apache.thrift.protocol.TProtocol iprot, get_result struct)
					throws org.apache.thrift.TException {
				org.apache.thrift.protocol.TField schemeField;
				iprot.readStructBegin();
				while (true) {
					schemeField = iprot.readFieldBegin();
					if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
						break;
					}
					switch (schemeField.id) {
					case 0: // SUCCESS
						if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
							struct.success = iprot.readString();
							struct.setSuccessIsSet(true);
						} else {
							org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
						}
						break;
					default:
						org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
					}
					iprot.readFieldEnd();
				}
				iprot.readStructEnd();

				// check for required fields of primitive type, which can't be
				// checked in the validate method
				struct.validate();
			}

			public void write(org.apache.thrift.protocol.TProtocol oprot, get_result struct)
					throws org.apache.thrift.TException {
				struct.validate();

				oprot.writeStructBegin(STRUCT_DESC);
				if (struct.success != null) {
					oprot.writeFieldBegin(SUCCESS_FIELD_DESC);
					oprot.writeString(struct.success);
					oprot.writeFieldEnd();
				}
				oprot.writeFieldStop();
				oprot.writeStructEnd();
			}

		}

		private static class get_resultTupleSchemeFactory implements SchemeFactory {
			public get_resultTupleScheme getScheme() {
				return new get_resultTupleScheme();
			}
		}

		private static class get_resultTupleScheme extends TupleScheme<get_result> {

			@Override
			public void write(org.apache.thrift.protocol.TProtocol prot, get_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol oprot = (TTupleProtocol) prot;
				BitSet optionals = new BitSet();
				if (struct.isSetSuccess()) {
					optionals.set(0);
				}
				oprot.writeBitSet(optionals, 1);
				if (struct.isSetSuccess()) {
					oprot.writeString(struct.success);
				}
			}

			@Override
			public void read(org.apache.thrift.protocol.TProtocol prot, get_result struct)
					throws org.apache.thrift.TException {
				TTupleProtocol iprot = (TTupleProtocol) prot;
				BitSet incoming = iprot.readBitSet(1);
				if (incoming.get(0)) {
					struct.success = iprot.readString();
					struct.setSuccessIsSet(true);
				}
			}
		}

	}

}
