using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class addT_beiansTable : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "seq",
                table: "t_nameplate",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "setPlace",
                table: "t_nameplate",
                maxLength: 200,
                nullable: true);

            migrationBuilder.AlterColumn<int>(
                name: "quantity",
                table: "t_invoice_detail",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(18,2)");

            migrationBuilder.AddColumn<int>(
                name: "investmentId",
                table: "t_invoice_detail",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "seq",
                table: "t_invoice",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<DateTime>(
                name: "voucherDate",
                table: "t_invoice",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "emodal",
                table: "t_investment",
                maxLength: 100,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<decimal>(
                name: "loadFactor",
                table: "t_investment",
                type: "decimal(5,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "runRate",
                table: "t_investment",
                type: "decimal(5,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "usagePower",
                table: "t_investment",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AlterColumn<int>(
                name: "quantity",
                table: "t_equipment",
                nullable: false,
                oldClrType: typeof(decimal),
                oldType: "decimal(18,2)");

            migrationBuilder.AlterColumn<string>(
                name: "producePlace",
                table: "t_equipment",
                maxLength: 200,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 200);

            migrationBuilder.AddColumn<decimal>(
                name: "loadFactor",
                table: "t_equipment",
                type: "decimal(5,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "runRate",
                table: "t_equipment",
                type: "decimal(5,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "usagePower",
                table: "t_equipment",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<decimal>(
                name: "amount",
                table: "t_contract_detail",
                type: "decimal(18,2)",
                nullable: false,
                defaultValue: 0m);

            migrationBuilder.AddColumn<int>(
                name: "investmentId",
                table: "t_contract_detail",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<bool>(
                name: "partPurchase",
                table: "t_contract_detail",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<int>(
                name: "quantity",
                table: "t_contract_detail",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<bool>(
                name: "secondHand",
                table: "t_contract_detail",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "traderPurchase",
                table: "t_contract_detail",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<int>(
                name: "seq",
                table: "t_contract",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<string>(
                name: "constructionPlace",
                table: "t_beian",
                maxLength: 500,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 200,
                oldNullable: true);

            migrationBuilder.AddColumn<string>(
                name: "accountName",
                table: "t_beian",
                maxLength: 20,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "accountPassword",
                table: "t_beian",
                maxLength: 50,
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "changedCnt",
                table: "t_beian",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<decimal>(
                name: "energyUsed",
                table: "t_beian",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "equipmentCnt",
                table: "t_beian",
                nullable: true);

            migrationBuilder.AddColumn<int>(
                name: "equipmentQuantity",
                table: "t_beian",
                nullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "powerUsed",
                table: "t_beian",
                type: "decimal(18,2)",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "projectNo",
                table: "t_beian",
                maxLength: 50,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "t_beian_changed",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    beianId = table.Column<int>(nullable: false),
                    changeLetterDate = table.Column<DateTime>(nullable: true),
                    changeContent = table.Column<string>(maxLength: 2000, nullable: true),
                    changeFilePath = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_beian_changed", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_beian_summary",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    beianId = table.Column<int>(nullable: false),
                    totalAmount = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    totalAmountTax = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    amountTax = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    equipment = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    equipmentTax = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    initWorkCapital = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    initWorkCapitalTax = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    construction = table.Column<decimal>(type: "decimal(18,2)", nullable: true),
                    constructionTax = table.Column<decimal>(type: "decimal(18,2)", nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_beian_summary", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "t_payment",
                columns: table => new
                {
                    id = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    creatorId = table.Column<int>(nullable: false),
                    lastUpdatorId = table.Column<int>(nullable: false),
                    createTime = table.Column<DateTime>(nullable: false),
                    lastUpdateTime = table.Column<DateTime>(nullable: false),
                    msCreatorId = table.Column<int>(nullable: false),
                    msLastUpdatorId = table.Column<int>(nullable: false),
                    companyId = table.Column<int>(nullable: false),
                    investmentId = table.Column<int>(nullable: false),
                    seq = table.Column<int>(nullable: false),
                    voucherNo = table.Column<string>(maxLength: 50, nullable: true),
                    voucherDate = table.Column<string>(maxLength: 50, nullable: true),
                    payType = table.Column<int>(nullable: false),
                    acceptDate = table.Column<DateTime>(nullable: true),
                    amount = table.Column<decimal>(type: "decimal(18,2)", nullable: false),
                    payDate = table.Column<DateTime>(nullable: false),
                    payee = table.Column<string>(maxLength: 50, nullable: false),
                    payStage = table.Column<int>(nullable: false),
                    payRate = table.Column<decimal>(type: "decimal(5,2)", nullable: false),
                    voucherPath = table.Column<string>(maxLength: 200, nullable: true),
                    bankReceiptPath = table.Column<string>(maxLength: 200, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_t_payment", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "t_beian_changed");

            migrationBuilder.DropTable(
                name: "t_beian_summary");

            migrationBuilder.DropTable(
                name: "t_payment");

            migrationBuilder.DropColumn(
                name: "seq",
                table: "t_nameplate");

            migrationBuilder.DropColumn(
                name: "setPlace",
                table: "t_nameplate");

            migrationBuilder.DropColumn(
                name: "investmentId",
                table: "t_invoice_detail");

            migrationBuilder.DropColumn(
                name: "seq",
                table: "t_invoice");

            migrationBuilder.DropColumn(
                name: "voucherDate",
                table: "t_invoice");

            migrationBuilder.DropColumn(
                name: "emodal",
                table: "t_investment");

            migrationBuilder.DropColumn(
                name: "loadFactor",
                table: "t_investment");

            migrationBuilder.DropColumn(
                name: "runRate",
                table: "t_investment");

            migrationBuilder.DropColumn(
                name: "usagePower",
                table: "t_investment");

            migrationBuilder.DropColumn(
                name: "loadFactor",
                table: "t_equipment");

            migrationBuilder.DropColumn(
                name: "runRate",
                table: "t_equipment");

            migrationBuilder.DropColumn(
                name: "usagePower",
                table: "t_equipment");

            migrationBuilder.DropColumn(
                name: "amount",
                table: "t_contract_detail");

            migrationBuilder.DropColumn(
                name: "investmentId",
                table: "t_contract_detail");

            migrationBuilder.DropColumn(
                name: "partPurchase",
                table: "t_contract_detail");

            migrationBuilder.DropColumn(
                name: "quantity",
                table: "t_contract_detail");

            migrationBuilder.DropColumn(
                name: "secondHand",
                table: "t_contract_detail");

            migrationBuilder.DropColumn(
                name: "traderPurchase",
                table: "t_contract_detail");

            migrationBuilder.DropColumn(
                name: "seq",
                table: "t_contract");

            migrationBuilder.DropColumn(
                name: "accountName",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "accountPassword",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "changedCnt",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "energyUsed",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "equipmentCnt",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "equipmentQuantity",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "powerUsed",
                table: "t_beian");

            migrationBuilder.DropColumn(
                name: "projectNo",
                table: "t_beian");

            migrationBuilder.AlterColumn<decimal>(
                name: "quantity",
                table: "t_invoice_detail",
                type: "decimal(18,2)",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<decimal>(
                name: "quantity",
                table: "t_equipment",
                type: "decimal(18,2)",
                nullable: false,
                oldClrType: typeof(int));

            migrationBuilder.AlterColumn<string>(
                name: "producePlace",
                table: "t_equipment",
                maxLength: 200,
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 200,
                oldNullable: true);

            migrationBuilder.AlterColumn<string>(
                name: "constructionPlace",
                table: "t_beian",
                maxLength: 200,
                nullable: true,
                oldClrType: typeof(string),
                oldMaxLength: 500,
                oldNullable: true);
        }
    }
}
