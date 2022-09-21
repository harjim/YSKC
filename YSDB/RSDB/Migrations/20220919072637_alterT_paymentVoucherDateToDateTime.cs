using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace RSDB.Migrations
{
    public partial class alterT_paymentVoucherDateToDateTime : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AlterColumn<DateTime>(
                name: "voucherDate",
                table: "t_payment",
                nullable: false,
                oldClrType: typeof(string),
                oldMaxLength: 50,
                oldNullable: true);

            migrationBuilder.AddColumn<decimal>(
                name: "taxRate",
                table: "t_contract_detail",
                type: "decimal(5,4)",
                nullable: false,
                defaultValue: 0m);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "taxRate",
                table: "t_contract_detail");

            migrationBuilder.AlterColumn<string>(
                name: "voucherDate",
                table: "t_payment",
                maxLength: 50,
                nullable: true,
                oldClrType: typeof(DateTime));
        }
    }
}
